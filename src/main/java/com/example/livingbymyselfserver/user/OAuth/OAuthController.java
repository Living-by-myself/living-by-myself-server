package com.example.livingbymyselfserver.user.OAuth;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.security.TokenResponseDto;
import com.example.livingbymyselfserver.user.OAuth.google.OAuthGoogleService;
import com.example.livingbymyselfserver.user.OAuth.kakao.OAuthKakaoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/home/auth")
public class OAuthController {
    private final OAuthKakaoService oAuthKakaoService;
    private final OAuthGoogleService oAuthGoogleService;

    @GetMapping("/kakao")
    public ResponseEntity<TokenResponseDto> getKakaoAccessToken(@RequestParam String code, HttpServletResponse response) {
        TokenResponseDto result = oAuthKakaoService.getKakaoAccessToken(code, response);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Operation(summary = "Google 로그인")
    @GetMapping("/login/oauth2/code/google")
    public String getGoogleAccessToken(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        oAuthGoogleService.googleLogin(code, response);

        return "redirect:/";
    }
}
