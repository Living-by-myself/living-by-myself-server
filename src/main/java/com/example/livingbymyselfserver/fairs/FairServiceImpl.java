package com.example.livingbymyselfserver.fairs;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.fairs.dto.FairRequestDto;
import com.example.livingbymyselfserver.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FairServiceImpl implements FairService {
  final private FairRepository fairRepository;
  @Override
  public ApiResponseDto createFair(User user, FairRequestDto requestDto) {
    Fair fair = new Fair(requestDto,user);
    fairRepository.save(fair);
    return new ApiResponseDto("커뮤니티 게시글 생성 완료", 201);

  }
}
