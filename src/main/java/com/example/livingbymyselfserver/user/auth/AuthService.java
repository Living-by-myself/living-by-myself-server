package com.example.livingbymyselfserver.user.auth;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.user.User;
import jakarta.servlet.http.HttpServletResponse;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

public interface AuthService {
  // 휴대폰 인증번호 전송
  ApiResponseDto sendMessage(PhoneMessageRequestDto requestDto) throws CoolsmsException;

  // 인증번호 확인
  ApiResponseDto authMessageCodePassword(PhoneMessageRequestDto requestDto, HttpServletResponse response);

  ApiResponseDto authMessageCodeSignup(PhoneMessageRequestDto requestDto);
  // password 변경
  ApiResponseDto updatePassword(User user, PasswordRequestDto passwordRequestDto);


}
