package com.example.livingbymyselfserver.user.profile;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.community.dto.CommunityResponseDto;
import com.example.livingbymyselfserver.groupBuying.dto.GroupBuyingResponseDto;
import com.example.livingbymyselfserver.user.User;
import com.example.livingbymyselfserver.user.profile.dto.OtherUserProfileResponseDto;
import com.example.livingbymyselfserver.user.profile.dto.ProfileRequestDto;
import com.example.livingbymyselfserver.user.profile.dto.ProfileResponseDto;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;


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

  // 관심등록 조회

  // 게시물 조회 (자취꿀팁)
  List<CommunityResponseDto> getCommunityByUser(Long userId);

  // 게시물 조회 (같이살때)
  List<GroupBuyingResponseDto> getGroupBuyingByUser(Long userId);

  // 지원한 공구 목록 조회
//  List<GroupBuyingResponseDto> getApplicationByUser(Long userId);
}
