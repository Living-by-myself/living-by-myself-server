package com.example.livingbymyselfserver.community;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.community.dto.CommunityDetailResponseDto;
import com.example.livingbymyselfserver.community.dto.CommunityRequestDto;
import com.example.livingbymyselfserver.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/home/communities")
public class CommunityController {
    private final CommunityService communityService;

    @GetMapping("/{communityId}")
    public ResponseEntity<CommunityDetailResponseDto> getCommunityDetailInfo(@PathVariable Long communityId) {
        CommunityDetailResponseDto result = communityService.getCommunityDetailInfo(communityId);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping
    public ResponseEntity<ApiResponseDto> createCommunity(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommunityRequestDto requestDto) {
        ApiResponseDto result = communityService.createCommunity(userDetails.getUser(), requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{communityId}")
    public ResponseEntity<ApiResponseDto> updateCommunity(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long communityId, @RequestBody CommunityRequestDto requestDto) {
        ApiResponseDto result = communityService.updateCommunity(userDetails.getUser(),communityId, requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/{communityId}")
    public ResponseEntity<ApiResponseDto> deleteCommunity(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long communityId) {
        ApiResponseDto result = communityService.deleteCommunity(userDetails.getUser(), communityId);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
