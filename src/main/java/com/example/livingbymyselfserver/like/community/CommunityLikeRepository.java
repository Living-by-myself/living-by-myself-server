package com.example.livingbymyselfserver.like.community;

import com.example.livingbymyselfserver.community.Community;
import com.example.livingbymyselfserver.like.entity.CommunityLike;
import com.example.livingbymyselfserver.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityLikeRepository extends JpaRepository<CommunityLike, Long> {
    Boolean existsByCommunityAndUser(Community community, User user);

    CommunityLike findByCommunityAndUser(Community community, User user);

    int countAllByCommunity(Community community);
}
