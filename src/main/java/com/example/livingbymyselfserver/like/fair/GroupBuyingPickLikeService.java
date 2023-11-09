package com.example.livingbymyselfserver.like.fair;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.user.User;

public interface GroupBuyingPickLikeService {
    ApiResponseDto createGroupBuyingPickLike(User user, Long groupBuyingId);

    ApiResponseDto deleteGroupBuyingPickLike(User user, Long groupBuyingId);
}
