package com.example.livingbymyselfserver.like.community;

import com.example.livingbymyselfserver.comment.community.CommunityCommentService;
import com.example.livingbymyselfserver.comment.entity.CommunityComment;
import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.community.Community;
import com.example.livingbymyselfserver.community.CommunityService;
import com.example.livingbymyselfserver.like.entity.CommunityCommentLike;
import com.example.livingbymyselfserver.like.entity.CommunityLike;
import com.example.livingbymyselfserver.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommunityLikeServiceImpl implements CommunityLikeService {
    private final CommunityService communityService;
    private final CommunityLikeRepository communityLikeRepository;
    private final CommunityCommentService communityCommentService;
    private final CommunityCommentLikeRepository communityCommentLikeRepository;
    @Override
    public ApiResponseDto createCommunityLike(User user, Long communityId) {
        Community community = communityService.findCommunity(communityId);

        if (communityLikeRepository.existsByCommunityAndUser(community, user)) {
            throw new IllegalArgumentException("이미 좋아요를 누른 유저 입니다.");
        }

        CommunityLike communityLike = new CommunityLike(user, community);
        communityLikeRepository.save(communityLike);

        return new ApiResponseDto("커뮤니티 좋아요 성공", 201);
    }

    @Override
    public ApiResponseDto deleteCommunityLike(User user, Long communityId) {
        Community community = communityService.findCommunity(communityId);

        if (!communityLikeRepository.existsByCommunityAndUser(community, user)) {
            throw new IllegalArgumentException("커뮤니티 좋아요를 누른 유저가 아닙니다.");
        }

        CommunityLike communityLike = communityLikeRepository.findByCommunityAndUser(community, user);
        communityLikeRepository.delete(communityLike);

        return new ApiResponseDto("커뮤니티 좋아요 취소", 200);
    }

    @Override
    public ApiResponseDto createCommunityCommentLike(User user, Long commentId) {
        CommunityComment comment = communityCommentService.findCommunityComment(commentId);

        if (communityCommentLikeRepository.existsByCommunityCommentAndUser(comment, user)) {
            throw new IllegalArgumentException("이미 좋아요를 누른 유저 입니다.");
        }

        CommunityCommentLike communityCommentLike = new CommunityCommentLike(user,comment);
        communityCommentLikeRepository.save(communityCommentLike);

        return new ApiResponseDto("커뮤니티 댓글 좋아요 성공", 201);
    }

    @Override
    public ApiResponseDto deleteCommunityCommentLike(User user, Long commentId) {
        CommunityComment comment = communityCommentService.findCommunityComment(commentId);

        if (!communityCommentLikeRepository.existsByCommunityCommentAndUser(comment, user)) {
            throw new IllegalArgumentException("커뮤니티 댓글 좋아요를 누른 유저가 아닙니다.");
        }

        CommunityCommentLike communityCommentLike = communityCommentLikeRepository.findByCommunityCommentAndUser(comment, user);
        communityCommentLikeRepository.delete(communityCommentLike);

        return new ApiResponseDto("커뮤니티 댓글 좋아요 취소", 200);
    }
}
