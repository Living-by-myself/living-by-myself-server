package com.example.livingbymyselfserver.common.scheduled;

import com.example.livingbymyselfserver.config.redis.RedisViewCountUtil;
import com.example.livingbymyselfserver.groupBuying.GroupBuying;
import com.example.livingbymyselfserver.groupBuying.GroupBuyingRepository;
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
    if (!redisUtil.getAllViewedPosts().isEmpty()) {
      for (TypedTuple<String> tuple : redisUtil.getAllViewedPosts()) {
        Long score = Objects.requireNonNull(tuple.getScore()).longValue();
        Long value = Long.parseLong(Objects.requireNonNull(tuple.getValue()));

//        GroupBuying groupBuying = groupBuyingRepository.findPostById(value);
//        groupBuying.setViewCnt(score);
      }
    }
  }
}