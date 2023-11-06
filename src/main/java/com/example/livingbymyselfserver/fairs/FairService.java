package com.example.livingbymyselfserver.fairs;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.fairs.dto.FairRequestDto;
import com.example.livingbymyselfserver.user.User;
import org.springframework.http.ResponseEntity;


public interface FairService {
  public ApiResponseDto createFair(User user, FairRequestDto requestDto);
}
