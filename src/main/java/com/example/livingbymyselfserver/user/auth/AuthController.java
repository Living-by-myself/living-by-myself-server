package com.example.livingbymyselfserver.user.auth;


import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User Auth API", description = "유저 비밀번호 인증 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/home/auth")
public class AuthController {

  private final AuthService authService;

  @Operation(summary = "휴대폰 인증번호 전송")
  @PostMapping("/message")
  public ResponseEntity<ApiResponseDto> sendMessage(@RequestBody PhoneMessageRequestDto requestDto,@RequestParam(value = "authentication", required = false) String keyword) throws Exception {
    ApiResponseDto result = authService.sendMessage(requestDto, keyword);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @Operation(summary = "휴대폰 인증번호 확인 - 비밀번호 찾기")
  @PostMapping("/message-code")
  public ResponseEntity<ApiResponseDto> confirmMessageCodePassword(@RequestBody PhoneMessageRequestDto requestDto, HttpServletResponse response) {
    ApiResponseDto result = authService.authMessageCodePassword(requestDto, response);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @Operation(summary = "휴대폰 인증번호 확인 - 회원가입")
  @PostMapping("/message-code/signup")
  public ResponseEntity<ApiResponseDto> confirmMessageCodeSignup(@RequestBody PhoneMessageRequestDto requestDto) {
    ApiResponseDto result = authService.authMessageCodeSignup(requestDto);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @Operation(summary = "비밀번호 재설정")
  @PatchMapping("/new-password")
  public ResponseEntity<ApiResponseDto> updatePassword(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PasswordRequestDto requestDto) {
    ApiResponseDto result = authService.updatePassword(userDetails.getUser(), requestDto);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

}
