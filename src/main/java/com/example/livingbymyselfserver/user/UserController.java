package com.example.livingbymyselfserver.user;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.user.dto.LoginRequestDto;
import com.example.livingbymyselfserver.user.dto.SignupRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<ApiResponseDto> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
    ApiResponseDto result = userService.login(requestDto, response);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

}
