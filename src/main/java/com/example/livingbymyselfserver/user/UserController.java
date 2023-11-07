package com.example.livingbymyselfserver.user;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.security.JwtUtil;
import com.example.livingbymyselfserver.security.TokenResponseDto;
import com.example.livingbymyselfserver.security.UserDetailsImpl;
import com.example.livingbymyselfserver.user.dto.LoginRequestDto;
import com.example.livingbymyselfserver.user.dto.SignupRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.nimbusds.oauth2.sdk.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User API", description = "계정인증 전 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/home/users")
@Slf4j(topic = "UserController")
public class UserController {

  private final UserService userService;

  @Operation(summary = "회원가입")
  @PostMapping("/signup")
  public ResponseEntity<ApiResponseDto> signup(@RequestBody SignupRequestDto requestDto) {
    ApiResponseDto result = userService.signup(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }

  @Operation(summary = "로그인")
  @PostMapping("/login")
  public ResponseEntity<TokenResponseDto> login(@RequestBody LoginRequestDto requestDto) {
    TokenResponseDto result = userService.login(requestDto);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }


  // 변경 필요
  @Operation(summary = "엑세스 토큰 재발급")
  @GetMapping("/reissue")
  public TokenResponseDto reissue(
      @AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletRequest request) {
    TokenResponseDto result = userService.reissue(userDetails.getUser(), request);
    return result;
  }
}
