package com.example.livingbymyselfserver.fairs;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.fairs.dto.FairRequestDto;
import com.example.livingbymyselfserver.fairs.dto.FairResponseDto;
import com.example.livingbymyselfserver.user.User;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface FairService {
  ApiResponseDto createFair(User user, FairRequestDto requestDto);
  ApiResponseDto updateFair(User user, Long fairId, FairRequestDto requestDto);
  ApiResponseDto deleteFair(Long id, User user);
  Fair findFair(Long id);

  FairResponseDto getFair(User user, Long fairId);

  ApiResponseDto applicationFair(User user, Long fairId);

  ApiResponseDto applicationDeleteFair(User user, Long fairId);

  ApiResponseDto closeFair(Long fairId, User user);

  List<FairResponseDto> getFairs(User user, Pageable pageable);
}
