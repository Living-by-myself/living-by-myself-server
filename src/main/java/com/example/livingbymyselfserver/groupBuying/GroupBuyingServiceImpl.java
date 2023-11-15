package com.example.livingbymyselfserver.groupBuying;

import com.example.livingbymyselfserver.attachment.S3Service;
import com.example.livingbymyselfserver.attachment.entity.Attachment;
import com.example.livingbymyselfserver.attachment.entity.AttachmentGroupBuyingUrl;
import com.example.livingbymyselfserver.attachment.fair.AttachmentGroupBuyingUrlRepository;
import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.common.PostTypeEnum;
import com.example.livingbymyselfserver.common.RedisUtil;
import com.example.livingbymyselfserver.common.RedisViewCountUtil;
import com.example.livingbymyselfserver.groupBuying.application.ApplicationUsers;
import com.example.livingbymyselfserver.groupBuying.application.ApplicationUsersRepository;
import com.example.livingbymyselfserver.groupBuying.dto.GroupBuyingListResponseDto;
import com.example.livingbymyselfserver.groupBuying.dto.GroupBuyingRequestDto;
import com.example.livingbymyselfserver.groupBuying.dto.GroupBuyingResponseDto;
import com.example.livingbymyselfserver.groupBuying.enums.GroupBuyingCategoryEnum;
import com.example.livingbymyselfserver.groupBuying.enums.GroupBuyingShareEnum;
import com.example.livingbymyselfserver.groupBuying.enums.GroupBuyingStatusEnum;
import com.example.livingbymyselfserver.groupBuying.repository.GroupBuyingRepository;
import com.example.livingbymyselfserver.user.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class GroupBuyingServiceImpl implements GroupBuyingService {

  final private GroupBuyingRepository groupBuyingRepository;
  final private ApplicationUsersRepository applicationUsersRepository;
  final private RedisViewCountUtil redisViewCountUtil;
  final private RedisUtil redisUtil;
  private final S3Service s3Service;
  private final AttachmentGroupBuyingUrlRepository attachmentGroupBuyingUrlRepository;

  @Override
  public GroupBuyingListResponseDto searchGroupBuyingList(Pageable pageable,
      String keyword, GroupBuyingCategoryEnum category, GroupBuyingShareEnum enumShare,
      GroupBuyingStatusEnum status, String beobJeongDong,String sort) {

    Page<GroupBuying> groupBuyingPage = groupBuyingRepository.searchItemList(pageable, keyword,
        category,enumShare,status,beobJeongDong,sort); //전체 크기를 받아오기 위한 Page
    Long totalLen =  groupBuyingPage.getTotalElements();  //total길이

    List<GroupBuyingResponseDto> groupBuyingResponseDtoList = groupBuyingRepository.searchItemList(pageable,keyword,
        category,enumShare,status,beobJeongDong,sort)
        .stream().map(GroupBuyingResponseDto::new)
        .toList();

    return new GroupBuyingListResponseDto(groupBuyingResponseDtoList, totalLen);
  }
  @Override
  public Long searchGroupBuyingListCnt(GroupBuyingCategoryEnum category,
      GroupBuyingShareEnum enumShare,
      GroupBuyingStatusEnum status, String beobJeongDong) {
    return groupBuyingRepository.searchGroupBuyingListSize(category, enumShare,status,beobJeongDong);

  }
  @Override
  public ApiResponseDto createGroupBuying(User user, String requestDto, MultipartFile[] multipartFiles) throws JsonProcessingException {
    GroupBuyingRequestDto groupBuyingRequestDto = conversionRequestDto(requestDto);

    GroupBuying groupBuying = new GroupBuying(groupBuyingRequestDto, user);
    groupBuyingRepository.save(groupBuying);

    if (!Objects.equals(multipartFiles[0].getOriginalFilename(), "")) {
      uploadImage(multipartFiles, groupBuying);
    }

    return new ApiResponseDto("공동구매 게시글 생성완료", 201);
  }

  @Override
  @Transactional
  public ApiResponseDto updateGroupBuying(User user, Long groupBuyingId, String requestDto, MultipartFile[] multipartFiles) throws JsonProcessingException {
    GroupBuying groupBuying = findGroupBuying(groupBuyingId);

    GroupBuyingRequestDto groupBuyingRequestDto = conversionRequestDto(requestDto);

    groupBuyingUserVerification(groupBuying, user);

    if (!Objects.equals(multipartFiles[0].getOriginalFilename(), "")) {
      updateGroupBuyingS3Image(groupBuying, multipartFiles);
    }

    groupBuying.updateGroupBuying(groupBuyingRequestDto);

    return new ApiResponseDto("공동구매 게시글 수정완료", 200);
  }

  @Override
  @Transactional
  public ApiResponseDto deleteGroupBuying(Long id, User user) {
    GroupBuying groupBuying = findGroupBuying(id);
    groupBuyingUserVerification(groupBuying,user);

    AttachmentGroupBuyingUrl attachmentUrl = attachmentGroupBuyingUrlRepository.findByGroupBuying(groupBuying);

    if (attachmentUrl != null) {
      String[] fileNames = attachmentUrl.getFileName().split(",");

      for (String fileName : fileNames) {
        s3Service.deleteFile(fileName);
      }

      attachmentGroupBuyingUrlRepository.deleteByGroupBuying(groupBuying);
    }

    groupBuyingRepository.delete(groupBuying);
    redisUtil.delete("GroupBuying:"+groupBuying);

    return new ApiResponseDto("공동구매 게시글 삭제완료", 200);
  }

  @Override
  public GroupBuyingResponseDto getGroupBuying(User user, Long groupBuyingId) {
    GroupBuying groupBuying = findGroupBuying(groupBuyingId);

    // 조회수 증가 로직
    if (redisViewCountUtil.checkAndIncrementViewCount(groupBuyingId.toString(),
        user.getId().toString(), PostTypeEnum.GROUPBUYING)) { // 조회수를 1시간이내에 올린적이 있는지 없는지 판단
      redisViewCountUtil.incrementPostViewCount(groupBuying.getId().toString(),PostTypeEnum.GROUPBUYING);
    }

    double viewCount = redisViewCountUtil.getViewPostCount(groupBuyingId.toString(),PostTypeEnum.GROUPBUYING) ==null?1:redisViewCountUtil.getViewPostCount(groupBuyingId.toString(),PostTypeEnum.GROUPBUYING);

    return new GroupBuyingResponseDto(groupBuying,viewCount);
  }

  @Override
  @Transactional
  public ApiResponseDto createApplication(User user, Long groupBuyingId) {

    GroupBuying groupBuying = findGroupBuying(groupBuyingId);
    if (applicationUsersRepository.existsByGroupBuyingAndUser(groupBuying, user))
      throw new IllegalArgumentException("이미 신청한 공고입니다.");
    if (groupBuying.getEnumStatus() == GroupBuyingStatusEnum.DEADLINE)
      throw new IllegalArgumentException("마감된 공고입니다.");
    //유저에 캐쉬 추가한 뒤 캐쉬 확인 차감작업
    if (user.getCash() < groupBuying.getPerUserPrice()) {
      throw new IllegalArgumentException("캐쉬가 부족합니다. 충전해주세요!");
    }
    if (groupBuying.getAppUsers().size() + 1 > groupBuying.getMaxUser()) {
      throw new IllegalArgumentException("인원이 가득 찼습니다.");
    }
    ApplicationUsers applicationUsers = new ApplicationUsers(user, groupBuying);
    user.setCash(user.getCash()-groupBuying.getPerUserPrice());
    applicationUsersRepository.save(applicationUsers);
    return new ApiResponseDto("공동구매 신청완료", 200);
    }



  @Override
  @Transactional
  public ApiResponseDto deleteApplication(User user, Long groupBuyingId) {
    GroupBuying groupBuying = findGroupBuying(groupBuyingId);

    if (!applicationUsersRepository.existsByGroupBuyingAndUser(groupBuying, user)) {
      throw new IllegalArgumentException("해당 공고를 신청한 유저가 아닙니다.");
    }
    redisUtil.delete("GroupBuying:"+user.getId()+groupBuyingId);
    //삭제시 redis내 같은 키값 조회수 데이터도 삭제해주기
    ApplicationUsers applicationUsers = applicationUsersRepository.findByGroupBuyingAndUser(groupBuying, user);
    user.setCash(user.getCash()+groupBuying.getPerUserPrice());
    applicationUsersRepository.delete(applicationUsers);

    return new ApiResponseDto("공고 지원 취소", 200);
  }

  @Override
  public List<GroupBuyingResponseDto> getGroupBuyingList(User user, Pageable pageable) {
    return groupBuyingRepository.findCategory(GroupBuyingCategoryEnum.FOOD ,pageable)
        .stream()
        .map(GroupBuyingResponseDto::new)
        .collect(Collectors.toList());
//    return groupBuyingRepository.findAll(pageable)
//        .stream()
//        .map(GroupBuyingResponseDto::new)
//        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public ApiResponseDto closeGroupBuying(Long groupBuyingId, User user) {
    GroupBuying groupBuying = findGroupBuying(groupBuyingId);

    groupBuyingUserVerification(groupBuying,user);

    groupBuying.setStatus(GroupBuyingStatusEnum.DEADLINE);

    return new ApiResponseDto("공고가 마감상태로 변경 되었습니다.", 200);
  }


  public GroupBuying findGroupBuying(Long id) {
    return groupBuyingRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("찾는 게시글이 존재하지 않습니다."));
  }

  private void groupBuyingUserVerification(GroupBuying groupBuying, User user){
    if(!user.getUsername().equals(groupBuying.getHost().getUsername()))
      throw new IllegalArgumentException("게시글 주인이 아닙니다.");
  }

  private void uploadImage(MultipartFile[] multipartFiles, GroupBuying groupBuying) {
    List<String> filePaths = s3Service.uploadFiles(multipartFiles);
    String fileUrls = "";
    for (String fileUrl : filePaths) {
      fileUrls = fileUrls + "," + fileUrl;
    }

    String fileUrlResult = fileUrls.replaceFirst("^,", "");
    AttachmentGroupBuyingUrl file = new AttachmentGroupBuyingUrl(fileUrlResult, groupBuying);
    attachmentGroupBuyingUrlRepository.save(file);
  }

  private GroupBuyingRequestDto conversionRequestDto(String requestDto) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();

    return objectMapper.readValue(requestDto,GroupBuyingRequestDto.class);
  }

  private void updateGroupBuyingS3Image(GroupBuying groupBuying,  MultipartFile[] multipartFiles) {
    Attachment attachmentUrl = attachmentGroupBuyingUrlRepository.findByGroupBuying(groupBuying);

    if (attachmentUrl != null) {
      s3Service.updateS3Image(attachmentUrl, multipartFiles);
    } else {
      uploadImage(multipartFiles, groupBuying);
    }
  }
}