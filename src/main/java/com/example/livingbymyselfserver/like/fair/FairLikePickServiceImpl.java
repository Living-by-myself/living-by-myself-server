package com.example.livingbymyselfserver.like.fair;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.fairs.Fair;
import com.example.livingbymyselfserver.fairs.FairService;
import com.example.livingbymyselfserver.like.entity.FairLikePick;
import com.example.livingbymyselfserver.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FairLikePickServiceImpl implements FairLikePickService{
    private final FairService fairService;
    private final FairLikePickRepository fairLikePickRepository;

    @Override
    public ApiResponseDto createFairLikePick(User user, Long fairId) {
        Fair fair = fairService.findFair(fairId);

        if (fairLikePickRepository.existsByFairAndUser(fair, user)) {
            throw new IllegalArgumentException("이미 찜 목록에 있습니다.");
        }

        FairLikePick fairLikePick = new FairLikePick(user, fair);
        fairLikePickRepository.save(fairLikePick);

        return new ApiResponseDto("찜 성공", 201);
    }

    @Override
    public ApiResponseDto deleteFairLikePick(User user, Long fairId) {
        Fair fair = fairService.findFair(fairId);

        if (!fairLikePickRepository.existsByFairAndUser(fair, user)) {
            throw new IllegalArgumentException("게시글 찜을 누른 유저가 아닙니다.");
        }

        FairLikePick fairLikePick = fairLikePickRepository.findByFairAndUser(fair, user);
        fairLikePickRepository.delete(fairLikePick);

        return new ApiResponseDto("찜 취소", 200);
    }
}
