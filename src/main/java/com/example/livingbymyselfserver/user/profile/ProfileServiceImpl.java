package com.example.livingbymyselfserver.user.profile;

import com.example.livingbymyselfserver.attachment.S3Service;
import com.example.livingbymyselfserver.attachment.community.AttachmentCommunityUrlRepository;
import com.example.livingbymyselfserver.attachment.entity.AttachmentCommunityUrl;
import com.example.livingbymyselfserver.attachment.entity.AttachmentGroupBuyingUrl;
import com.example.livingbymyselfserver.attachment.entity.AttachmentUserUrl;
import com.example.livingbymyselfserver.attachment.fair.AttachmentGroupBuyingUrlRepository;
import com.example.livingbymyselfserver.attachment.user.AttachmentUserUrlRepository;
import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.common.PostTypeEnum;
import com.example.livingbymyselfserver.common.RedisViewCountUtil;
import com.example.livingbymyselfserver.community.dto.CommunityResponseDto;
import com.example.livingbymyselfserver.community.repository.CommunityRepository;
import com.example.livingbymyselfserver.groupBuying.application.ApplicationUsers;
import com.example.livingbymyselfserver.groupBuying.application.ApplicationUsersRepository;
import com.example.livingbymyselfserver.groupBuying.dto.GroupBuyingResponseDto;
import com.example.livingbymyselfserver.groupBuying.dto.GroupBuyingUserResponseDto;
import com.example.livingbymyselfserver.groupBuying.pickLike.GroupBuyingPickLike;
import com.example.livingbymyselfserver.groupBuying.pickLike.GroupBuyingPickLikeRepository;
import com.example.livingbymyselfserver.groupBuying.repository.GroupBuyingRepository;
import com.example.livingbymyselfserver.user.User;
import com.example.livingbymyselfserver.user.UserRepository;
import com.example.livingbymyselfserver.user.UserService;
import com.example.livingbymyselfserver.user.profile.dto.OtherUserProfileResponseDto;
import com.example.livingbymyselfserver.user.profile.dto.ProfileRequestDto;
import com.example.livingbymyselfserver.user.profile.dto.ProfileResponseDto;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService{

  private final UserRepository userRepository;
  private final AttachmentUserUrlRepository attachmentUserUrlRepository;
  private final S3Service s3Service;
  private final CommunityRepository communityRepository;
  private final UserService userService;
  private final AttachmentCommunityUrlRepository attachmentCommunityUrlRepository;
  private final RedisViewCountUtil redisViewCountUtil;
  private final GroupBuyingRepository groupBuyingRepository;
  private final AttachmentGroupBuyingUrlRepository attachmentGroupBuyingUrlRepository;
  private final ApplicationUsersRepository applicationUsersRepository;
  private final GroupBuyingPickLikeRepository groupBuyingPickLikeRepository;


  @Override
  public ProfileResponseDto getUserProfile(User user) {
    AttachmentUserUrl attachmentUserUrl = attachmentUserUrlRepository.findByUser(user);

    return (attachmentUserUrl != null) ?
            new ProfileResponseDto(user, attachmentUserUrl) :
            new ProfileResponseDto(user);
  }

  @Override
  @Transactional
  public ApiResponseDto updateUserProfileImage(User user, MultipartFile multipartFiles) {
    AttachmentUserUrl attachmentUserUrl = attachmentUserUrlRepository.findByUser(user);

    if (attachmentUserUrl == null) {
      String fileUrlResult = profileImageUpload(multipartFiles);

      AttachmentUserUrl file = new AttachmentUserUrl(user, fileUrlResult);

      attachmentUserUrlRepository.save(file);
    } else {
      String file = attachmentUserUrl.getFileName();
      s3Service.deleteFile(file);

      String replaceUploadFileName = profileImageUpload(multipartFiles);
      attachmentUserUrl.setFileName(replaceUploadFileName);
    }

    return new ApiResponseDto("프로필 이미지 설정", 200);
  }

  private String profileImageUpload(MultipartFile multipartFiles) {
    String filePath = s3Service.uploadFile(multipartFiles);

    return filePath.replaceFirst("^,", "");
  }

  @Override
  @Transactional
  public ApiResponseDto updateUserProfile(ProfileRequestDto requestDto, User user) {
    user.setNickname(requestDto.getNickName());
    user.setAddress(requestDto.getAddress());

    return new ApiResponseDto("프로필 수정 완료", 200);
  }

  @Override
  public OtherUserProfileResponseDto getOtherUserProfile(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
    AttachmentUserUrl attachmentUserUrl = attachmentUserUrlRepository.findByUser(user);

    return (attachmentUserUrl != null) ?
            new OtherUserProfileResponseDto(user, attachmentUserUrl) :
            new OtherUserProfileResponseDto(user);
  }

  @Override
  public List<CommunityResponseDto> getCommunityByUser(Long userId) {
    User user = userService.findUser(userId);
    return communityRepository.findAllByUser(user)
        .stream()
        .map(community -> {
          AttachmentCommunityUrl attachmentCommunityUrl = attachmentCommunityUrlRepository.findByCommunity(community);
          double viewCount = redisViewCountUtil.getViewPostCount(community.getId().toString(),
              PostTypeEnum.COMMUNITY);
          return (attachmentCommunityUrl == null) ? new CommunityResponseDto(community,viewCount) : new CommunityResponseDto(community, attachmentCommunityUrl,viewCount);
        })
        .collect(Collectors.toList());
  }

  @Override
  public List<GroupBuyingResponseDto> getGroupBuyingByUser(Long userId) {
    User user = userService.findUser(userId);
    return groupBuyingRepository.findAllByHost(user)
        .stream().map(groupBuying -> {
          double viewCount = redisViewCountUtil.getViewPostCount(groupBuying.getId().toString(),PostTypeEnum.GROUPBUYING);
          AttachmentGroupBuyingUrl attachmentGroupBuyingUrl = attachmentGroupBuyingUrlRepository.findByGroupBuying(groupBuying);
          return (attachmentGroupBuyingUrl == null) ? new GroupBuyingResponseDto(groupBuying,viewCount) : new GroupBuyingResponseDto(groupBuying, attachmentGroupBuyingUrl,viewCount);
        })
        .toList();
  }

  @Override
  public List<GroupBuyingUserResponseDto> getApplicationByUser(Long userId) {
    User user = userService.findUser(userId);
    List<ApplicationUsers> applicationUsersList = applicationUsersRepository.findAllByUser(user);

    return applicationUsersList.stream()
        .map(ApplicationUsers::getGroupBuying)
        .filter(Objects::nonNull) // null인 경우 필터링
        .map(groupBuying -> {
          AttachmentGroupBuyingUrl attachmentGroupBuyingUrl = attachmentGroupBuyingUrlRepository.findByGroupBuying(groupBuying);
          return (attachmentGroupBuyingUrl == null) ? new GroupBuyingUserResponseDto(groupBuying) : new GroupBuyingUserResponseDto(groupBuying, attachmentGroupBuyingUrl);
        })
        .toList();
  }

  @Override
  public List<GroupBuyingUserResponseDto> getGroupBuyingLikeByUser(Long userId) {
    User user = userService.findUser(userId);
    List<GroupBuyingPickLike> groupBuyingPickLikes = groupBuyingPickLikeRepository.findAllByUser(user);

    return groupBuyingPickLikes.stream()
        .map(GroupBuyingPickLike::getGroupBuying)
        .map(groupBuying -> {
          AttachmentGroupBuyingUrl attachmentGroupBuyingUrl = attachmentGroupBuyingUrlRepository.findByGroupBuying(groupBuying);
          return (attachmentGroupBuyingUrl==null) ? new GroupBuyingUserResponseDto(groupBuying) : new GroupBuyingUserResponseDto(groupBuying, attachmentGroupBuyingUrl);
        })
        .toList();
  }
}
