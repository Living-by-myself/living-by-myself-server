package com.example.livingbymyselfserver.report;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.user.User;
import com.example.livingbymyselfserver.user.dto.SignupRequestDto;

public interface ReportService {

  ApiResponseDto userReport(User user, ReportRequestDto requestDto, Long userId);
  ApiResponseDto reportApproval(User user ,Long reportId);
}
