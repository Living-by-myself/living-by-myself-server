package com.example.livingbymyselfserver.community.like;


import com.example.livingbymyselfserver.community.comment.CommunityComment;
import com.example.livingbymyselfserver.community.like.entity.CommunityCommentLike;
import com.example.livingbymyselfserver.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityCommentLikeRepository extends JpaRepository<CommunityCommentLike, Long> {
    Boolean existsByCommunityCommentAndUser(CommunityComment communityComment, User user);

    CommunityCommentLike findByCommunityCommentAndUser(CommunityComment communityComment, User user);
}
