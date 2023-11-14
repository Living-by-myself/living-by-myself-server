package com.example.livingbymyselfserver.user.OAuth.google;

import com.example.livingbymyselfserver.common.RedisUtil;
import com.example.livingbymyselfserver.security.JwtUtil;
import com.example.livingbymyselfserver.security.TokenResponseDto;
import com.example.livingbymyselfserver.user.OAuthProviderEnum;
import com.example.livingbymyselfserver.user.User;
import com.example.livingbymyselfserver.user.UserRepository;
import com.example.livingbymyselfserver.user.UserRoleEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuthGoogleService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String secretId;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;


    public TokenResponseDto googleLogin(String code, HttpServletResponse response) throws JsonProcessingException {
        // "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getToken(code);
        log.info("googleLogin accessToken: " + accessToken);

        // 토큰으로 구글 API 호출 : "액세스 토큰"으로 "구글 사용자 정보" 가져오기
        GoogleInfoResponse googleUserInfo = getUserInfo(accessToken);

        // 회원가입 또는 로그인
        User googleUser = saveOrUpdate(googleUserInfo);

        accessToken = jwtUtil.createToken(googleUser.getUsername(), googleUser.getRole());
        String refreshToken = jwtUtil.createRefreshToken(googleUser.getUsername(), googleUser.getRole());

        redisUtil.saveRefreshToken(googleUser.getUsername(), refreshToken);

        return new TokenResponseDto(accessToken, refreshToken);
    }


    public String getToken(String code) throws JsonProcessingException {

        RestTemplate restTemplate = new RestTemplate();

        log.info("getToken code: " + code);
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("https://oauth2.googleapis.com")
                .path("/token")
                .encode()
                .build()
                .toUri();

        // HTTP Header 생성: application/x-www-form-urlencoded 형식의 본문을 가진 POST 요청을 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded");

        // HTTP Body 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("client_secret", secretId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(params);

        // HTTP 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
                requestEntity,
                String.class // 반환값 타입은 String
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 값을 반환합니다.
        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        return jsonNode.get("access_token").asText();
    }

    private GoogleInfoResponse getUserInfo(String accessToken) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        String userInfoUrl = "https://www.googleapis.com/oauth2/v3/userinfo";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        ResponseEntity<String> response = restTemplate.exchange(
                userInfoUrl,
                HttpMethod.GET,
                new HttpEntity<String>("", headers),
                String.class
        );

        ObjectMapper mapper = new ObjectMapper();
        GoogleInfoResponse googleInfo = mapper.readValue(response.getBody(), GoogleInfoResponse.class);
        return googleInfo;
    }

    private User saveOrUpdate(GoogleInfoResponse googleInfo) {
        User user = userRepository.findByUsername(googleInfo.getUsername()).orElse(null);

        if (user == null) {
            user = new User(googleInfo.getUsername(), null, UserRoleEnum.MEMBER, OAuthProviderEnum.GOOGLE);
            userRepository.save(user);
        }

        return user;
    }
}
