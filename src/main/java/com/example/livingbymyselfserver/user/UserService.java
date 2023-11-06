package com.example.livingbymyselfserver.user;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.user.dto.LoginRequestDto;
import com.example.livingbymyselfserver.user.dto.SignupRequestDto;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
  ApiResponseDto signup(SignupRequestDto dto);

  ApiResponseDto login(LoginRequestDto dto, HttpServletResponse response);

  User findUser(String username);

}
