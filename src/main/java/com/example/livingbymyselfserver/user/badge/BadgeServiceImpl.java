package com.example.livingbymyselfserver.user.badge;

import com.example.livingbymyselfserver.community.Community;
import com.example.livingbymyselfserver.community.CommunityRepository;
import com.example.livingbymyselfserver.like.community.CommunityLikeRepository;
import com.example.livingbymyselfserver.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BadgeServiceImpl implements BadgeService {
    private final BadgeRepository badgeRepository;
    private final CommunityRepository communityRepository;
    private final CommunityLikeRepository communityLikeRepository;

    @Override
    public void addBadgeForCommunityCount(User user) {
        int communityCnt = communityRepository.countAllByUser(user);
        Badge badge;
        Boolean check;

        if (communityCnt == 5) {
            badge = new Badge(BadgeEnum.seed, user);
            badgeRepository.save(badge);
        }

        switch (communityCnt) {
            case 5 -> {
                check = checkUserBadge(user, BadgeEnum.seed);
                if (check) break;
                badge = new Badge(BadgeEnum.seed, user);
                badgeRepository.save(badge);
            }
            case 10 -> {
                check = checkUserBadge(user, BadgeEnum.sprout);
                if (check) break;
                badge = new Badge(BadgeEnum.sprout, user);
                badgeRepository.save(badge);
            }
            case 20 -> {
                check = checkUserBadge(user, BadgeEnum.branch);
                if (check) break;
                badge = new Badge(BadgeEnum.branch, user);
                badgeRepository.save(badge);
            }
            case 50 -> {
                check = checkUserBadge(user, BadgeEnum.flower);
                if (check) break;
                badge = new Badge(BadgeEnum.flower, user);
                badgeRepository.save(badge);
            }
            case 100 -> {
                check = checkUserBadge(user, BadgeEnum.tree);
                if (check) break;
                badge = new Badge(BadgeEnum.tree, user);
                badgeRepository.save(badge);
            }
        }
    }

    @Override
    public void addBadgeForCommunityLike(Community community) {
        int communityLikeCnt = communityLikeRepository.countAllByCommunity(community);
        User user = community.getUser();
        Badge badge;
        Boolean check;

        switch (communityLikeCnt) {
            case 50 -> {
                check = checkUserBadge(user, BadgeEnum.good);
                if (check) break;
                badge = new Badge(BadgeEnum.good, user);
                badgeRepository.save(badge);
            }
            case 100 -> {
                check = checkUserBadge(user, BadgeEnum.perfect);
                if (check) break;
                badge = new Badge(BadgeEnum.perfect, user);
                badgeRepository.save(badge);
            }
        }
    }

    private Boolean checkUserBadge(User user, BadgeEnum badge) {
        Badge findBadge= badgeRepository.findByUserAndBadgeEnum(user, badge);

        return findBadge != null;
    }
}
