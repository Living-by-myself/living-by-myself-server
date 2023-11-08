package com.example.livingbymyselfserver.comment.community;

import com.example.livingbymyselfserver.comment.dto.CommentRequestDto;
import com.example.livingbymyselfserver.comment.dto.CommunityCommentResponseDto;
import com.example.livingbymyselfserver.comment.entity.CommunityComment;
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
