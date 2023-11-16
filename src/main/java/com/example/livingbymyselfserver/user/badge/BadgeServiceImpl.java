package com.example.livingbymyselfserver.user.badge;

import com.example.livingbymyselfserver.alarm.AlarmCategoryEnum;
import com.example.livingbymyselfserver.alarm.KafkaProducer;
import com.example.livingbymyselfserver.alarm.NotificationMessage;
import com.example.livingbymyselfserver.common.RedisViewCountUtil;
import com.example.livingbymyselfserver.community.Community;
import com.example.livingbymyselfserver.community.CommunityRepository;
import com.example.livingbymyselfserver.like.community.CommunityLikeRepository;
import com.example.livingbymyselfserver.user.User;
import com.example.livingbymyselfserver.user.badge.dto.BadgeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BadgeServiceImpl implements BadgeService {
    private final BadgeRepository badgeRepository;
    private final CommunityRepository communityRepository;
    private final CommunityLikeRepository communityLikeRepository;
    private final RedisViewCountUtil redisViewCountUtil;
    private final KafkaProducer kafkaProducer;

    @Override
    public void addBadgeForCommunityCount(User user) {
        int communityCnt = communityRepository.countAllByUser(user);
        Badge badge;
        Boolean check;

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

    @Override
    public List<BadgeResponseDto> getBadgeList(User user) {
        List<Badge> badgeList = badgeRepository.findAllByUser(user);

        return badgeList.stream().map(BadgeResponseDto::new).collect(Collectors.toList());
    }

    public void addBadgeForCommunityView(Community community) {
        double count = redisViewCountUtil.getViewPostCount(String.valueOf(community.getId()), null);
        boolean check;
        User user = community.getUser();
        Badge badge;

        switch ((int) count) {
            case 100 -> {
                check = checkUserBadge(user, BadgeEnum.popular);
                if (check) break;
                badge = new Badge(BadgeEnum.popular, user);
                badgeRepository.save(badge);
            }
            case 500 -> {
                check = checkUserBadge(user, BadgeEnum.celebrity);
                if (check) break;
                badge = new Badge(BadgeEnum.celebrity, user);
                badgeRepository.save(badge);
            }
        }
    }

    private Boolean checkUserBadge(User user, BadgeEnum badge) {
        Badge findBadge= badgeRepository.findByUserAndBadgeEnum(user, badge);

        return findBadge != null;
    }
}
