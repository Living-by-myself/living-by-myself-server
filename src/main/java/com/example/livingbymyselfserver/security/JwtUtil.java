package com.example.livingbymyselfserver.security;

import com.example.livingbymyselfserver.common.RedisUtil;
import com.example.livingbymyselfserver.user.User;
import com.example.livingbymyselfserver.user.UserRoleEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j(topic = "JwtUtil")
@Component
@RequiredArgsConstructor
public class JwtUtil {
  private final RedisUtil redisUtil;

  // Header KEY 값
  public static final String AUTHORIZATION_HEADER = "Authorization";
  // 사용자 권한 값의 KEY
  public static final String AUTHORIZATION_KEY = "auth";
  // Token 식별자
  public static final String BEARER_PREFIX = "Bearer ";

  // 토큰 만료시간
  public static final long REFRESH_TOKEN_TIME = 7 * 24 * 60 * 60 * 1000L; // 7일
  public static final long TOKEN_TIME = 60 * 60 * 1000L; // 60분

  @Value("${jwt.secret.key}") // Base64 Encoded SecretKey
  private String secretKey;
  private Key key;
  private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

  @PostConstruct
  public void init() {
    byte[] bytes = Base64.getDecoder().decode(secretKey);
    key = Keys.hmacShaKeyFor(bytes);
  }

  public String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    // token 이 null 또는 공백인지 체크 && 토큰이 정상적으로 Bearer 를 가지고 있는지 체크
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
      return bearerToken.substring(7);
    }
    return null;
  }

  // access token 토큰 생성
  public String createToken(String username, UserRoleEnum role) {
    Date date = new Date();

    return BEARER_PREFIX +
        Jwts.builder()
            .setSubject(username) // 사용자 식별자값(ID)
            .claim(AUTHORIZATION_KEY, role)  //사용자 권한
            .setExpiration(new Date(date.getTime() + TOKEN_TIME)) // 만료 시간
            .setIssuedAt(date) // 발급일
            .signWith(key, signatureAlgorithm) // 암호화 알고리즘
            .compact();
  }


  // refresh token 생성
  public String createRefreshToken(String username, UserRoleEnum role) {
    Date now = new Date();

    return BEARER_PREFIX + Jwts.builder()
        .setSubject(username) // 사용자 식별자값(ID)
        .claim(AUTHORIZATION_KEY, role)  //사용자 권한
        .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_TIME))  //만료시간
        .signWith(key, signatureAlgorithm)
        .compact();
  }


  // JWT 검증
  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      if (redisUtil.hasKeyBlackList(token)) { // 로그아웃한 토큰인지 확인 (redis에 key값으로 존재 유무 확인)
        // TODO 에러 발생시키는 부분 수정
        throw new RuntimeException("로그아웃한 토큰");
      }
      return true;
    } catch (SecurityException | MalformedJwtException | SignatureException e) {
      log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
    } catch (ExpiredJwtException e) {
      log.error("Expired JWT token, 만료된 JWT token 입니다.");
    } catch (UnsupportedJwtException e) {
      log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
    } catch (IllegalArgumentException e) {
      log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
    }
    return false;
  }

  // refreshToken 확인
  public boolean checkRefreshToken(String username, String token) {
    String storedToken = redisUtil.getRefreshToken(username);
    return storedToken !=null && storedToken.equals(token);
  }

  // atk 남은 만료 시간
  public Long expireTime(String token) {
    // 토큰 만료 시간
    Long expirationTime = getUserInfoFromToken(token).getExpiration().getTime();
    // 현재 시간
    Long dateTime = new Date().getTime();

    return expirationTime - dateTime;
  }



  // 토큰에서 사용자 정보 가져오기
  public Claims getUserInfoFromToken(String token) {
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
  }

  public String getUsernameFromToken(String token) {
    Claims claims = getUserInfoFromToken(token);
    return claims.get("username", String.class);
  }

}