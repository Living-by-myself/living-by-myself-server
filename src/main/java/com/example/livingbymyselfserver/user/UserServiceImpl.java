package com.example.livingbymyselfserver.user;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.security.JwtUtil;
import com.example.livingbymyselfserver.user.dto.LoginRequestDto;
import com.example.livingbymyselfserver.user.dto.SignupRequestDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  @Override
  public ApiResponseDto signup(SignupRequestDto dto) {
    String username = dto.getUsername();
    String password = passwordEncoder.encode(dto.getPassword());
    String phoneNumber =dto.getPhoneNumber();

    UserRoleEnum role = UserRoleEnum.MEMBER;
    OAuthProviderEnum oAuthProviderEnum = OAuthProviderEnum.ORIGIN;

    // 비밀번호 확인
    if(!passwordEncoder.matches(dto.getPasswordCheck(), password)) {
      throw new IllegalArgumentException("비밀번호가 맞지 않습니다.");
    }
    User user = new User(username, password, username, phoneNumber, role, oAuthProviderEnum);
    userRepository.save(user);
    return new ApiResponseDto("회원가입 성공", 201);
  }

  @Override
  public ApiResponseDto login(LoginRequestDto dto, HttpServletResponse response) {
    String username = dto.getUsername();
    String password = dto.getPassword();

    User user = findUser(username);

    // password 확인
    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new IllegalArgumentException("회원을 찾을 수 없습니다.");
    }

    // JWT 생성 및 헤더에 추가
    String token = jwtUtil.createToken(username);
    response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);


    return new ApiResponseDto("로그인 완료", 200);
  }

  @Override
  public User findUser(String username) {
    return userRepository.findByUsername(username).orElseThrow(()->
        new IllegalArgumentException("회원을 찾을 수 없습니다."));
  }
}
