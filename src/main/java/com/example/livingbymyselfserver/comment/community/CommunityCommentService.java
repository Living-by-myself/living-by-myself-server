package com.example.livingbymyselfserver.comment.community;

import com.example.livingbymyselfserver.comment.dto.CommentRequestDto;
import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.user.User;

public interface CommunityCommentService {
    ApiResponseDto createCommunityComment(User user, Long communityId, CommentRequestDto requestDto);

    ApiResponseDto updateCommunityComment(User user, Long commentId, CommentRequestDto requestDto);

    ApiResponseDto deleteCommunityComment(User user, Long commentId);
}
