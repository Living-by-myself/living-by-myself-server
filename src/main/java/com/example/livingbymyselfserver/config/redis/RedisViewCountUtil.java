package com.example.livingbymyselfserver.config.redis;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisViewCountUtil {

  private final RedisTemplate<String, String> redis;

  // 조회수 증가 가능 여부 확인
  public boolean checkAndIncrementViewCount(String groupBuyingId, String userId) {
    String userKey = "GroupBuying:" + groupBuyingId + ":" + userId;
    Long addedCount = redis.opsForSet().add(userKey, userId);

    if (addedCount == 1L) {
      redis.expire(userKey, 60, TimeUnit.SECONDS);
      return true; // 조회 가능한 경우 조회수 증가
    }
    return false; // 이미 조회한 경우 조회수 증가하지 않음
  }

  // post 조회수 확인 *
  public Double getViewPostCount(String groupBuyingId) {
    return redis.opsForZSet().score("GroupBuying:", groupBuyingId);
  }

  // post 조회수 증가 로직 *
  public void incrementPostViewCount(String groupBuyingId) {
    redis.opsForZSet().incrementScore("GroupBuying:", groupBuyingId, 1);
  }

  public Set<TypedTuple<String>> getAllViewedPosts() {
    return redis.opsForZSet().reverseRangeWithScores("GroupBuying:", 0, -1);
  }

}