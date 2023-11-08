package com.example.livingbymyselfserver.fairs;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.community.Community;
import com.example.livingbymyselfserver.fairs.application.ApplicationUsers;
import com.example.livingbymyselfserver.fairs.application.ApplicationUsersRepository;
import com.example.livingbymyselfserver.fairs.dto.FairRequestDto;
import com.example.livingbymyselfserver.fairs.dto.FairResponseDto;
import com.example.livingbymyselfserver.like.entity.CommunityLike;
import com.example.livingbymyselfserver.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FairServiceImpl implements FairService {
  final private FairRepository fairRepository;
  final private ApplicationUsersRepository applicationUsersRepository;
  @Override
  public ApiResponseDto createFair(User user, FairRequestDto requestDto) {
    Fair fair = new Fair(requestDto,user);
    fairRepository.save(fair);
    return new ApiResponseDto("공동구매 게시글 생성완료", 201);

  }
  @Override
  @Transactional
  public ApiResponseDto updateFair(User user, Long fairId, FairRequestDto requestDto) {
    Fair fair = findFair(fairId);

    fairUserVerification(fair, user);
    fair.updateFair(requestDto);

    return new ApiResponseDto("공동구매 게시글 수정완료", 200);
  }
  @Override
  public ApiResponseDto deleteFair(Long id, User user) {
    Fair fair =findFair(id);
    fairUserVerification(fair,user);
    fairRepository.delete(fair);

    return new ApiResponseDto("공동구매 게시글 삭제완료", 200);
  }

  @Override
  public FairResponseDto getFair(User user, Long fairId) {
    Fair fair = findFair(fairId);

    return new FairResponseDto(fair);
  }

  @Override
  @Transactional
  public ApiResponseDto applicationFair(User user, Long fairId) {

    Fair fair = findFair(fairId);
    if(applicationUsersRepository.existsByFairAndUser(fair, user))
      throw new IllegalArgumentException("이미 신청한 공고입니다.");
    //유저에 캐쉬 추가한 뒤 캐쉬 확인 차감작업
//    if(user.getCash()<fair.getPerUserPrice()){
//      throw new IllegalArgumentException("캐쉬가 부족합니다. 충전해주세요!");
//    }
    if(fair.getAppUsers().size()+2 >fair.getMaxUser()){
      throw new IllegalArgumentException("인원이 가득 찼습니다.");
    }
    ApplicationUsers applicationUsers = new ApplicationUsers(user,fair);
    applicationUsersRepository.save(applicationUsers);


    return new ApiResponseDto("공동구매 신청완료", 200);
  }

  @Override
  @Transactional
  public ApiResponseDto applicationDeleteFair(User user, Long fairId) {
    Fair fair = findFair(fairId);

    if (!applicationUsersRepository.existsByFairAndUser(fair, user)) {
      throw new IllegalArgumentException("해당 공고를 신청한 유저가 아닙니다.");
    }

    //유저 캐쉬 업데이트시 캐쉬 반환 로직 추가하기

    ApplicationUsers applicationUsers = applicationUsersRepository.findByFairAndUser(fair, user);
    applicationUsersRepository.delete(applicationUsers);

    return new ApiResponseDto("공고 지원 취소", 200);
  }


  public Fair findFair(Long id) {
    return fairRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("찾는 게시글이 존재하지 않습니다."));
  }
  private void fairUserVerification(Fair fair, User user){
    if(!user.getUsername().equals(fair.getHost().getUsername()))
      throw new IllegalArgumentException("게시글 주인이 아닙니다.");
  }
}
