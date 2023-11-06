package com.example.livingbymyselfserver.community;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.community.dto.CommunityRequestDto;
import com.example.livingbymyselfserver.user.User;

public interface CommunityService {
    ApiResponseDto createCommunity(User user, CommunityRequestDto requestDto);

    ApiResponseDto updateCommunity(User user, Long communityId, CommunityRequestDto requestDto);

    ApiResponseDto deleteCommunity(User user, Long communityId);
}
