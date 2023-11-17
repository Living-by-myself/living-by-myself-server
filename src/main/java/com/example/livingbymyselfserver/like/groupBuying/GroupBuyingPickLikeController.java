package com.example.livingbymyselfserver.like.groupBuying;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/home/group-buying")
@Tag(name = "GroupBuying 찜 API", description = "찜 API")
public class GroupBuyingPickLikeController {
    private final GroupBuyingPickLikeService groupBuyingPickLikeService;

    @Operation(summary = "찜")
    @PostMapping("/{groupBuyingId}/pick-like")
    public ResponseEntity<ApiResponseDto> createGroupBuyingPickLike(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long groupBuyingId) {
        ApiResponseDto result = groupBuyingPickLikeService.createGroupBuyingPickLike(userDetails.getUser(), groupBuyingId);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Operation(summary = "찜 취소")
    @DeleteMapping("/{groupBuyingId}/pick-like")
    public ResponseEntity<ApiResponseDto> deleteGroupBuyingPickLike(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long groupBuyingId) {
        ApiResponseDto result = groupBuyingPickLikeService.deleteGroupBuyingPickLike(userDetails.getUser(), groupBuyingId);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
