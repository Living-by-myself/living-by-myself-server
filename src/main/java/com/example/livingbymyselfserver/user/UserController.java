package com.example.livingbymyselfserver.user;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.security.TokenResponseDto;
import com.example.livingbymyselfserver.security.UserDetailsImpl;
import com.example.livingbymyselfserver.user.auth.PhoneMessageRequestDto;
import com.example.livingbymyselfserver.user.dto.CashRequestDto;
import com.example.livingbymyselfserver.user.dto.LoginRequestDto;
import com.example.livingbymyselfserver.user.dto.SignupRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User API", description = "유저 계정 인증 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/home/users")
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
  @Operation(summary = "엑세스 토큰 재발급")
  @GetMapping("/reissue")
  public TokenResponseDto reissue(
      @AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletRequest request) {
    TokenResponseDto result = userService.reissue(userDetails.getUser(), request);
    return result;
  }

  @Operation(summary = "로그아웃")
  @DeleteMapping("/logout")
  public ResponseEntity<ApiResponseDto> logout(@AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletRequest request) {
    ApiResponseDto result = userService.logout(userDetails.getUser(), request);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @Operation(summary = "캐쉬 충전")
  @PutMapping("/cash")
  public ResponseEntity<ApiResponseDto> buyingCash(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CashRequestDto cashRequestDto) {
    ApiResponseDto result = userService.buyingCash(userDetails.getUser(),cashRequestDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }


}
