package com.example.livingbymyselfserver.user;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.common.RedisUtil;
import com.example.livingbymyselfserver.security.JwtUtil;
import com.example.livingbymyselfserver.security.TokenResponseDto;
import com.example.livingbymyselfserver.user.dto.LoginRequestDto;
import com.example.livingbymyselfserver.user.dto.SignupRequestDto;
import jakarta.servlet.http.HttpServletRequest;
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
  private final RedisUtil redisUtil;

  @Override
  public ApiResponseDto signup(SignupRequestDto dto) {
    String username = dto.getUsername();
    String password = passwordEncoder.encode(dto.getPassword());
    String phoneNumber =dto.getPhoneNumber();
    UserRoleEnum role = UserRoleEnum.MEMBER;
    OAuthProviderEnum oAuthProviderEnum = OAuthProviderEnum.ORIGIN;

    // 비밀번호 확인
    passwordCheck(dto.getPasswordCheck(), password);

    User user = new User(username, password, username, phoneNumber, role, oAuthProviderEnum);
    userRepository.save(user);

    return new ApiResponseDto("회원가입 성공", 201);
  }

  @Override
  public TokenResponseDto login(LoginRequestDto dto) {
    String username = dto.getUsername();
    String password = dto.getPassword();

    User user = findUser(username);

    // password 확인
    passwordCheck(password, user.getPassword());

    // JWT 생성 및 헤더에 추가
    String atk = jwtUtil.createToken(username, user.getRole());
    String rtk = jwtUtil.createRefreshToken(username, user.getRole());

    // RefreshToken Redis 저장
    redisUtil.saveRefreshToken(user.getUsername(), rtk);

    return new TokenResponseDto(atk, rtk);
  }

  @Override
  public TokenResponseDto reissue(User user, HttpServletRequest request) {
    String newArk = jwtUtil.createToken(user.getUsername(), user.getRole());
    return new TokenResponseDto(newArk, null);
  }

  @Override
  public User findUser(String username) {
    return userRepository.findByUsername(username).orElseThrow(()->
        new IllegalArgumentException("회원을 찾을 수 없습니다."));
  }

  private void passwordCheck(CharSequence rawPassword, String password) {
    if(!passwordEncoder.matches(rawPassword, password)) {
      throw new IllegalArgumentException("회원을 찾을 수 없습니다.");
    }
  }
}
