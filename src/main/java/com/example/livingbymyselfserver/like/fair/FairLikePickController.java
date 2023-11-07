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
@RequestMapping("/home/fairs")
public class FairLikePickController {
    private final FairLikePickService fairLikePickService;

    @PostMapping("/{fairId}/likePick")
    public ResponseEntity<ApiResponseDto> createFairLikePick(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long fairId) {
        ApiResponseDto result = fairLikePickService.createFairLikePick(userDetails.getUser(), fairId);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping("/{fairId}/likePick")
    public ResponseEntity<ApiResponseDto> deleteFairLikePick(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long fairId) {
        ApiResponseDto result = fairLikePickService.deleteFairLikePick(userDetails.getUser(), fairId);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
