package com.example.livingbymyselfserver.fairs;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.fairs.dto.FairRequestDto;
import com.example.livingbymyselfserver.fairs.dto.FairResponseDto;
import com.example.livingbymyselfserver.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FairServiceImpl implements FairService {
  final private FairRepository fairRepository;
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


  private Fair findFair(Long id) {
    return fairRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("찾는 게시글이 존재하지 않습니다."));
  }
  private void fairUserVerification(Fair fair, User user){
    if(!user.getUsername().equals(fair.getHost().getUsername()))
      throw new IllegalArgumentException("게시글 주인이 아닙니다.");
  }
}
