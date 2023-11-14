package com.example.livingbymyselfserver.user.auth;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.common.RedisUtil;
import com.example.livingbymyselfserver.security.JwtUtil;
import com.example.livingbymyselfserver.user.User;
import com.example.livingbymyselfserver.user.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final RedisUtil redisUtil;
  private final JwtUtil jwtUtil;

  @Value("${coolsms.devHee.apikey}")
  private String apiKey;

  @Value("${coolsms.devHee.apisecret}")
  private String apiSecret;

  @Value("${coolsms.devHee.fromnumber}")
  private String fromNumber;

  @Override
  public ApiResponseDto sendMessage(PhoneMessageRequestDto requestDto) throws CoolsmsException {
    User user = findUserByPhoneNumber(requestDto.getPhoneNumber());

    // 난수 생성 (0~9999)
    Random random = new Random();
    int randomNumber = random.nextInt(10000);
    // 난수를 4자리 문자열로 변환 (무조건 4자리수가 나오도록하기위해서 )
    String formattedRandomNumber = String.format("%04d", randomNumber);
    // 유효기간 3분
    redisUtil.set(requestDto.getPhoneNumber(), formattedRandomNumber, 3);

    Message coolsms = new Message(apiKey, apiSecret);

    HashMap<String, String> params = new HashMap<String, String>();

    params.put("to", requestDto.getPhoneNumber());
    params.put("from", fromNumber);
    params.put("type", "SMS");
    params.put("text", "[혼자살때] 인증번호 [" + formattedRandomNumber + "] 를 입력해주세요.");
    params.put("app_version", "app 1"); // application name and version

    try {
      JSONObject obj = coolsms.send(params);
      System.out.println(obj.toString());

    } catch (CoolsmsException e) {
      throw new CoolsmsException(e.getMessage(), e.getCode());
    }

    return new ApiResponseDto("핸드폰 인증번호 전송", 200);
  }

  @Override
  public ApiResponseDto authMessageCodePassword(PhoneMessageRequestDto requestDto, HttpServletResponse response) {

    confirmMessageCode(requestDto.getPhoneNumber(), requestDto.getCode());

    User user = findUserByPhoneNumber(requestDto.getPhoneNumber());

    // 엑세스 토큰 헤더에 추가
    String atk = jwtUtil.createToken(user.getUsername(), user.getRole());
    response.addHeader(JwtUtil.AUTHORIZATION_HEADER, atk);

    return new ApiResponseDto("핸드폰 인증번호 확인", 200);
  }

  @Override
  public ApiResponseDto authMessageCodeSignup(PhoneMessageRequestDto requestDto) {

    confirmMessageCode(requestDto.getPhoneNumber(), requestDto.getCode());

    return new ApiResponseDto("핸드폰 인증번호 확인", 200);
  }

  @Override
  public ApiResponseDto updatePassword(User user, PasswordRequestDto passwordRequestDto) {
    String newPassword = passwordRequestDto.getNewPassword();
    String newPasswordCheck = passwordRequestDto.getNewPasswordCheck();

    if(!newPassword.equals(newPasswordCheck)) {
      throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
    }
    String encodePassword = passwordEncoder.encode(newPassword);
    user.setPassword(encodePassword);

    return new ApiResponseDto("비밀번호 재설정 완료", 200);
  }

  private User findUserByPhoneNumber(String phoneNumber) {
    return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(
        ()-> new IllegalArgumentException("핸드폰 번호와 일치하는 유저가 존재하지 않습니다."));
  }

  private void confirmMessageCode(String phoneNumber, int requestCode) {
    String getCode = redisUtil.get(phoneNumber);

    if (getCode == null) {
      throw new IllegalArgumentException("만료시간이 지났습니다.");
    }

    int code = Integer.parseInt(getCode);

    if(code == requestCode) {
      redisUtil.delete(phoneNumber);
    } else {
      throw new IllegalArgumentException("인증 코드가 다릅니다.");
    }
  }
}
