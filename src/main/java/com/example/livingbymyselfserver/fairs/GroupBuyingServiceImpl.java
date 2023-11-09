package com.example.livingbymyselfserver.fairs;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.fairs.application.ApplicationUsers;
import com.example.livingbymyselfserver.fairs.application.ApplicationUsersRepository;
import com.example.livingbymyselfserver.fairs.dto.GroupBuyingRequestDto;
import com.example.livingbymyselfserver.fairs.dto.GroupBuyingResponseDto;
import com.example.livingbymyselfserver.user.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GroupBuyingServiceImpl implements GroupBuyingService {

  final private GroupBuyingRepository groupBuyingRepository;
  final private ApplicationUsersRepository applicationUsersRepository;

  @Override
  public ApiResponseDto createGroupBuying(User user, GroupBuyingRequestDto requestDto) {
    GroupBuying groupBuying = new GroupBuying(requestDto,user);
    groupBuyingRepository.save(groupBuying);

    return new ApiResponseDto("공동구매 게시글 생성완료", 201);
  }

  @Override
  @Transactional
  public ApiResponseDto updateGroupBuying(User user, Long groupBuyingId, GroupBuyingRequestDto requestDto) {
    GroupBuying groupBuying = findGroupBuying(groupBuyingId);

    groupBuyingUserVerification(groupBuying, user);
    groupBuying.updateGroupBuying(requestDto);

    return new ApiResponseDto("공동구매 게시글 수정완료", 200);
  }

  @Override
  public ApiResponseDto deleteGroupBuying(Long id, User user) {
    GroupBuying groupBuying = findGroupBuying(id);
    groupBuyingUserVerification(groupBuying,user);
    groupBuyingRepository.delete(groupBuying);

    groupBuying.setStatus(GroupBuyingStatusEnum.DEADLINE);  //공고 마감공고로 상태 변경

    return new ApiResponseDto("공동구매 게시글 삭제완료", 200);
  }

  @Override
  public GroupBuyingResponseDto getGroupBuying(User user, Long groupBuyingId) {
    GroupBuying groupBuying = findGroupBuying(groupBuyingId);

    return new GroupBuyingResponseDto(groupBuying);
  }

  @Override
  @Transactional
  public ApiResponseDto createApplication(User user, Long groupBuyingId) {

    GroupBuying groupBuying = findGroupBuying(groupBuyingId);
    if(applicationUsersRepository.existsByGroupBuyingAndUser(groupBuying, user))
      throw new IllegalArgumentException("이미 신청한 공고입니다.");
    if(groupBuying.getEnumStatus() == GroupBuyingStatusEnum.DEADLINE)
      throw new IllegalArgumentException("마감된 공고입니다.");
    //유저에 캐쉬 추가한 뒤 캐쉬 확인 차감작업
//    if(user.getCash()<fair.getPerUserPrice()){
//      throw new IllegalArgumentException("캐쉬가 부족합니다. 충전해주세요!");
//    }
    if(groupBuying.getAppUsers().size()+2 > groupBuying.getMaxUser()){
      throw new IllegalArgumentException("인원이 가득 찼습니다.");
    }
    ApplicationUsers applicationUsers = new ApplicationUsers(user, groupBuying);
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

    //유저 캐쉬 업데이트시 캐쉬 반환 로직 추가하기

    ApplicationUsers applicationUsers = applicationUsersRepository.findByGroupBuyingAndUser(groupBuying, user);
    applicationUsersRepository.delete(applicationUsers);

    return new ApiResponseDto("공고 지원 취소", 200);
  }

  @Override
  @Transactional
  public ApiResponseDto closeGroupBuying(Long groupBuyingId, User user) {
    GroupBuying groupBuying = findGroupBuying(groupBuyingId);

    groupBuyingUserVerification(groupBuying,user);

    groupBuying.setStatus(GroupBuyingStatusEnum.DEADLINE);

    return new ApiResponseDto("공고가 마감상태로 변경 되었습니다.", 200);
  }

  @Override
  public List<GroupBuyingResponseDto> getGroupBuyingList(User user, Pageable pageable) {

    return groupBuyingRepository.findAll(pageable)
        .stream()
        .map(GroupBuyingResponseDto::new)
        .collect(Collectors.toList());
  }


  public GroupBuying findGroupBuying(Long id) {
    return groupBuyingRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("찾는 게시글이 존재하지 않습니다."));
  }

  private void groupBuyingUserVerification(GroupBuying groupBuying, User user){
    if(!user.getUsername().equals(groupBuying.getHost().getUsername()))
      throw new IllegalArgumentException("게시글 주인이 아닙니다.");
  }
}