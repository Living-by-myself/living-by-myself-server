package com.example.livingbymyselfserver.common.scheduled;

import com.example.livingbymyselfserver.common.RedisViewCountUtil;
import com.example.livingbymyselfserver.groupBuying.GroupBuying;
import com.example.livingbymyselfserver.groupBuying.repository.GroupBuyingRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class viewCountScheduled {
  private final RedisViewCountUtil redisUtil;
  private final GroupBuyingRepository groupBuyingRepository;

  @Scheduled(cron = "0 0 */6 * * *")
  @Transactional
  public void myScheduledMethod() {
    if (!redisUtil.getAllViewedGroupBuyingPosts().isEmpty()) {
      for (TypedTuple<String> tuple : redisUtil.getAllViewedGroupBuyingPosts()) {
        Long score = Objects.requireNonNull(tuple.getScore()).longValue();  //조회수
        Long value = Long.parseLong(Objects.requireNonNull(tuple.getValue()));  //키 값(공고id)

        GroupBuying groupBuying = groupBuyingRepository.findById(value).orElseThrow(()->new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        groupBuying.setViewCnt(score);
      }
    }
    if (!redisUtil.getAllViewedCommunityPosts().isEmpty()) {
      for (TypedTuple<String> tuple : redisUtil.getAllViewedCommunityPosts()) {
        Long score = Objects.requireNonNull(tuple.getScore()).longValue();  //조회수
        Long value = Long.parseLong(Objects.requireNonNull(tuple.getValue()));  //키 값(공고id)

        GroupBuying groupBuying = groupBuyingRepository.findById(value).orElseThrow(()->new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        groupBuying.setViewCnt(score);
      }
    }

  }
}