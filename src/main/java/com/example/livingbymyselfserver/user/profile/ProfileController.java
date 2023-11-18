package com.example.livingbymyselfserver.user.profile;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.community.dto.CommunityResponseDto;
import com.example.livingbymyselfserver.groupBuying.dto.GroupBuyingResponseDto;
import com.example.livingbymyselfserver.security.UserDetailsImpl;
import com.example.livingbymyselfserver.user.badge.BadgeService;
import com.example.livingbymyselfserver.user.badge.dto.BadgeResponseDto;
import com.example.livingbymyselfserver.user.profile.dto.OtherUserProfileResponseDto;
import com.example.livingbymyselfserver.user.profile.dto.ProfileRequestDto;
import com.example.livingbymyselfserver.user.profile.dto.ProfileResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "User Profile API", description = "유저 프로필 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/home/profile")
public class ProfileController {
    private final ProfileService profileService;
    private final BadgeService badgeService;

    @Operation(summary = "유저 정보 조회")
    @GetMapping
    public ResponseEntity<ProfileResponseDto> getUserProfile(@AuthenticationPrincipal
        UserDetailsImpl userDetails) {
        ProfileResponseDto result = profileService.getUserProfile(userDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Operation(summary = "다른 유저 정보 조회")
    @GetMapping("/other/{userId}")
    public ResponseEntity<OtherUserProfileResponseDto> getOtherUserProfile(@PathVariable Long userId) {
        OtherUserProfileResponseDto result = profileService.getOtherUserProfile(userId);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Operation(summary = "유저 정보 수정")
    @PutMapping
    public ResponseEntity<ApiResponseDto> updateUserProfile(@RequestBody ProfileRequestDto requestDto,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto result = profileService.updateUserProfile(requestDto, userDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Operation(summary = "유저 이미지 수정")
    @PatchMapping("/image")
    public ResponseEntity<ApiResponseDto> updateUserProfileImage(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart("fileName") MultipartFile multipartFiles
    ) {
        ApiResponseDto result = profileService.updateUserProfileImage(userDetails.getUser(), multipartFiles);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Operation(summary = "유저 뱃지 조회")
    @GetMapping("/badge")
    public ResponseEntity<List<BadgeResponseDto>> getBadgeList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<BadgeResponseDto> result = badgeService.getBadgeList(userDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Operation(summary = "유저 게시물 조회(자취꿀팁)")
    @GetMapping("/community/{userId}")
    public ResponseEntity<List<CommunityResponseDto>> getCommunityByUser(@PathVariable Long userId) {
        List<CommunityResponseDto> result = profileService.getCommunityByUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Operation(summary = "유저 게시물 조회(같이살때)")
    @GetMapping("/group-buying/{userId}")
    public ResponseEntity<List<GroupBuyingResponseDto>> getGroupBuyingByUser(@PathVariable Long userId) {
        List<GroupBuyingResponseDto> result = profileService.getGroupBuyingByUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
