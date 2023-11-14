package com.example.livingbymyselfserver.user.profile;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.user.User;
import com.example.livingbymyselfserver.user.UserRepository;
import com.example.livingbymyselfserver.user.UserService;
import com.example.livingbymyselfserver.user.profile.dto.OtherUserProfileResponseDto;
import com.example.livingbymyselfserver.user.profile.dto.ProfileRequestDto;
import com.example.livingbymyselfserver.user.profile.dto.ProfileResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService{

  private final UserRepository userRepository;
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;


  @Override
  public ProfileResponseDto getUserProfile(User user) {
    return new ProfileResponseDto(user);
  }

  @Override
  @Transactional
  public ApiResponseDto updateUserProfile(ProfileRequestDto requestDto, User user) {
    user.setNickname(requestDto.getNickName());
    user.setAddress(requestDto.getAddress());

    return new ApiResponseDto("프로필 수정 완료", 200);
  }

  @Override
  public OtherUserProfileResponseDto getOtherUserProfile(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
    return new OtherUserProfileResponseDto(user);
  }


}
