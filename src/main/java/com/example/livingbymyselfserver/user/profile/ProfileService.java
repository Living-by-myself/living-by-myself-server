package com.example.livingbymyselfserver.user.profile;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.user.User;
import com.example.livingbymyselfserver.user.UserRepository;
import com.example.livingbymyselfserver.user.badge.dto.BadgeResponseDto;
import com.example.livingbymyselfserver.user.profile.dto.OtherUserProfileResponseDto;
import com.example.livingbymyselfserver.user.profile.dto.ProfileRequestDto;
import com.example.livingbymyselfserver.user.profile.dto.ProfileResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface ProfileService {

  // user 정보 조회
  ProfileResponseDto getUserProfile(User user);

  // user 정보 상세 조회

  // 프로필 이미지 수정
  ApiResponseDto updateUserProfileImage(User user, MultipartFile multipartFiles);

  // 프로필 정보 수정
  ApiResponseDto updateUserProfile(ProfileRequestDto requestDto, User user);

  // 다른 user 정보조회
  OtherUserProfileResponseDto getOtherUserProfile(Long userId);
}
