package com.example.livingbymyselfserver.user.OAuth;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.security.TokenResponseDto;
import com.example.livingbymyselfserver.user.OAuth.kakao.OAuthKakaoService;
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

    @GetMapping("/kakao")
    public ResponseEntity<TokenResponseDto> getKakaoAccessToken(@RequestParam String code, HttpServletResponse response) {
        TokenResponseDto result = oAuthKakaoService.getKakaoAccessToken(code, response);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
