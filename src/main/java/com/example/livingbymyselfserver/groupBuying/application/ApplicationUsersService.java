package com.example.livingbymyselfserver.groupBuying.application;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.user.User;


public interface ApplicationUsersService {
  ApiResponseDto createApplication(User user, Long groupBuyingId);

  ApiResponseDto deleteApplication(User user, Long groupBuyingId);
}
