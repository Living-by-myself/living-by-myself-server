package com.example.livingbymyselfserver.like.groupBuying;

import com.example.livingbymyselfserver.groupBuying.GroupBuying;
import com.example.livingbymyselfserver.like.entity.GroupBuyingPickLike;
import com.example.livingbymyselfserver.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupBuyingPickLikeRepository extends JpaRepository<GroupBuyingPickLike, Long> {
    Boolean existsByGroupBuyingAndUser(GroupBuying groupBuying, User user);

    GroupBuyingPickLike findByGroupBuyingAndUser(GroupBuying groupBuying, User user);
}
