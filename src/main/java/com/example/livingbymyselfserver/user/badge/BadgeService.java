package com.example.livingbymyselfserver.user.badge;

import com.example.livingbymyselfserver.community.Community;
import com.example.livingbymyselfserver.user.User;

public interface BadgeService {
    void addBadgeForCommunityCount(User user);

    void addBadgeForCommunityLike(Community community);
}
