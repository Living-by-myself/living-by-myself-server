package com.example.livingbymyselfserver.user;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.security.TokenResponseDto;
import com.example.livingbymyselfserver.user.auth.PhoneMessageRequestDto;
import com.example.livingbymyselfserver.user.dto.LoginRequestDto;
import com.example.livingbymyselfserver.user.dto.SignupRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
  ApiResponseDto signup(SignupRequestDto dto);

  TokenResponseDto login(LoginRequestDto dto);

  TokenResponseDto reissue(User user, HttpServletRequest request);

  User findUser(String username);

  User findUser(Long userId);

  ApiResponseDto logout(User user, HttpServletRequest request);
}
