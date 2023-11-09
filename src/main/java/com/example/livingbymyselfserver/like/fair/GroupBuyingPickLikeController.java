package com.example.livingbymyselfserver.like.fair;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/home/group-buying")
public class GroupBuyingPickLikeController {
    private final GroupBuyingPickLikeService groupBuyingPickLikeService;

    @PostMapping("/{groupBuyingId}/pick-like")
    public ResponseEntity<ApiResponseDto> createGroupBuyingPickLike(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long groupBuyingId) {
        ApiResponseDto result = groupBuyingPickLikeService.createGroupBuyingPickLike(userDetails.getUser(), groupBuyingId);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping("/{groupBuyingId}/pick-like")
    public ResponseEntity<ApiResponseDto> deleteGroupBuyingPickLike(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long groupBuyingId) {
        ApiResponseDto result = groupBuyingPickLikeService.deleteGroupBuyingPickLike(userDetails.getUser(), groupBuyingId);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
