package com.example.livingbymyselfserver.like.fair;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.fairs.GroupBuying;
import com.example.livingbymyselfserver.fairs.GroupBuyingService;
import com.example.livingbymyselfserver.like.entity.GroupBuyingPickLike;
import com.example.livingbymyselfserver.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupBuyingPickLikeServiceImpl implements GroupBuyingPickLikeService {
    private final GroupBuyingService groupBuyingService;
    private final GroupBuyingPickLikeRepository groupBuyingPickLikeRepository;

    @Override
    public ApiResponseDto createGroupBuyingPickLike(User user, Long groupBuyingId) {
        GroupBuying groupBuying = groupBuyingService.findGroupBuying(groupBuyingId);

        if (groupBuyingPickLikeRepository.existsByGroupBuyingAndUser(groupBuying, user)) {
            throw new IllegalArgumentException("이미 찜 목록에 있습니다.");
        }

        GroupBuyingPickLike groupBuyingPickLike = new GroupBuyingPickLike(user, groupBuying);
        groupBuyingPickLikeRepository.save(groupBuyingPickLike);

        return new ApiResponseDto("찜 성공", 201);
    }

    @Override
    public ApiResponseDto deleteGroupBuyingPickLike(User user, Long fairId) {
        GroupBuying groupBuying = groupBuyingService.findGroupBuying(fairId);

        if (!groupBuyingPickLikeRepository.existsByGroupBuyingAndUser(groupBuying, user)) {
            throw new IllegalArgumentException("게시글 찜을 누른 유저가 아닙니다.");
        }

        GroupBuyingPickLike groupBuyingPickLike = groupBuyingPickLikeRepository.findByGroupBuyingAndUser(groupBuying, user);
        groupBuyingPickLikeRepository.delete(groupBuyingPickLike);

        return new ApiResponseDto("찜 취소", 200);
    }
}
