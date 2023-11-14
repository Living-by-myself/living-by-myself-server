package com.example.livingbymyselfserver.common;

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
  public boolean checkAndIncrementViewCount(String id, String userId,PostTypeEnum postTypeEnum) {
    String userKey;
    if(postTypeEnum.equals(PostTypeEnum.GROUPBUYING))
      userKey = "GroupBuying:" + id + ":" + userId;
    else
      userKey = "Community" + id + ":" + userId;

      Long addedCount = redis.opsForSet().add(userKey, userId);
      if (addedCount == 1L) {
        redis.expire(userKey, 5, TimeUnit.SECONDS);
        return true; // 조회 가능한 경우 조회수 증가
      }
      return false; // 이미 조회한 경우 조회수 증가하지 않음
  }
  // 커뮤니티 조회수기능 따로 만들기
  public boolean communityCheckAndIncrementViewCount(String id, String userId,PostTypeEnum postTypeEnum) {
    String userKey;
    if(postTypeEnum.equals(PostTypeEnum.GROUPBUYING))
      userKey = "GroupBuying:" + id + ":" + userId;
    else
      userKey = "Community" + id + ":" + userId;
    Long addedCount = redis.opsForSet().add(userKey, userId);

    if (addedCount == 1L) {
      redis.expire(userKey, 5, TimeUnit.SECONDS);
      return true; // 조회 가능한 경우 조회수 증가
    }
    return false; // 이미 조회한 경우 조회수 증가하지 않음
  }


  // post 조회수 확인 *
  public Double getViewPostCount(String id, PostTypeEnum postTypeEnum) {
    if(postTypeEnum.equals(PostTypeEnum.GROUPBUYING))
      return redis.opsForZSet().score("GroupBuying:", id);
    else
      return redis.opsForZSet().score("Community:", id);
  }

  // post 조회수 증가 로직 *
  public void incrementPostViewCount(String id, PostTypeEnum postTypeEnum) {
    if(postTypeEnum.equals(PostTypeEnum.GROUPBUYING))
      redis.opsForZSet().incrementScore("GroupBuying:", id, 1);
    else
      redis.opsForZSet().incrementScore("Community:", id, 1);
  }

  public Set<TypedTuple<String>> getAllViewedCommunityPosts() {

      return redis.opsForZSet().reverseRangeWithScores("Community:", 0, -1);
  }
  public Set<TypedTuple<String>> getAllViewedGroupBuyingPosts() {

    return redis.opsForZSet().reverseRangeWithScores("GroupBuying:", 0, -1);
  }

}