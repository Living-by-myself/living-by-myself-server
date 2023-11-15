package com.example.livingbymyselfserver.user.badge;

import com.example.livingbymyselfserver.community.Community;
import com.example.livingbymyselfserver.user.User;
import com.example.livingbymyselfserver.user.badge.dto.BadgeResponseDto;

import java.util.List;

public interface BadgeService {
    void addBadgeForCommunityCount(User user);

    void addBadgeForCommunityLike(Community community);

    List<BadgeResponseDto> getBadgeList(User user);

    void addBadgeForCommunityView(Community community);
}
