package com.example.livingbymyselfserver.groupBuying.pickLike;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.user.User;

public interface GroupBuyingPickLikeService {
    ApiResponseDto createGroupBuyingPickLike(User user, Long groupBuyingId);

    ApiResponseDto deleteGroupBuyingPickLike(User user, Long groupBuyingId);
}
