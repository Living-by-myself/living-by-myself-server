package com.example.livingbymyselfserver.community.comment;

import com.example.livingbymyselfserver.community.comment.dto.CommentRequestDto;
import com.example.livingbymyselfserver.community.comment.dto.CommunityCommentResponseDto;
import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.user.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommunityCommentService {
    ApiResponseDto createCommunityComment(User user, Long communityId, CommentRequestDto requestDto);

    ApiResponseDto updateCommunityComment(User user, Long commentId, CommentRequestDto requestDto);

    ApiResponseDto deleteCommunityComment(User user, Long commentId);

    CommunityComment findCommunityComment(Long commentId);

    List<CommunityCommentResponseDto> getCommunityComments(Long communityId, Pageable pageable);
}
