package com.example.livingbymyselfserver.like.community;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/home/community")
public class CommunityLikeController {
    private final CommunityLikeService communityLikeService;

    @PostMapping("/{communityId}/like")
    public ResponseEntity<ApiResponseDto> createCommunityLike(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long communityId) {
        ApiResponseDto result = communityLikeService.createCommunityLike(userDetails.getUser(), communityId);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping("/{communityId}/like")
    public ResponseEntity<ApiResponseDto> deleteCommunityLike(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long communityId) {
        ApiResponseDto result = communityLikeService.deleteCommunityLike(userDetails.getUser(), communityId);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
