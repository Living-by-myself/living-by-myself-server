package com.example.livingbymyselfserver.like.community;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.user.User;

public interface CommunityLikeService {
    ApiResponseDto createCommunityLike(User user, Long communityId);

    ApiResponseDto deleteCommunityLike(User user, Long communityId);

    ApiResponseDto createCommunityCommentLike(User user, Long commentId);

    ApiResponseDto deleteCommunityCommentLike(User user, Long commentId);
}
