package com.example.livingbymyselfserver.community.comment;

import com.example.livingbymyselfserver.community.comment.dto.CommentRequestDto;
import com.example.livingbymyselfserver.community.comment.dto.CommunityCommentResponseDto;
import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/home/community")
@RequiredArgsConstructor
public class CommunityCommentController {
    private final CommunityCommentService communityCommentService;

    @GetMapping("/{communityId}/comments")
    public ResponseEntity<List<CommunityCommentResponseDto>> getCommunityComments(
            @PathVariable Long communityId,
            Pageable pageable){
        List<CommunityCommentResponseDto> result = communityCommentService.getCommunityComments(communityId, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/{communityId}/comments")
    public ResponseEntity<ApiResponseDto> createCommunityComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                 @PathVariable Long communityId,
                                                                 @RequestBody CommentRequestDto requestDto) {
        ApiResponseDto result = communityCommentService.createCommunityComment(userDetails.getUser(), communityId, requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponseDto> updateCommunityComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                 @PathVariable Long commentId,
                                                                 @RequestBody CommentRequestDto requestDto) {
        ApiResponseDto result = communityCommentService.updateCommunityComment(userDetails.getUser(), commentId, requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponseDto> deleteCommunityComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                 @PathVariable Long commentId) {
        ApiResponseDto result = communityCommentService.deleteCommunityComment(userDetails.getUser(), commentId);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
