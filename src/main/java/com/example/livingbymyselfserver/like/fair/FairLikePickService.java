package com.example.livingbymyselfserver.like.fair;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.user.User;

public interface FairLikePickService {
    ApiResponseDto createFairLikePick(User user, Long fairId);

    ApiResponseDto deleteFairLikePick(User user, Long fairId);
}
