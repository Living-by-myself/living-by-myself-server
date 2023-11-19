package com.example.livingbymyselfserver.groupBuying.application;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.common.RedisUtil;
import com.example.livingbymyselfserver.groupBuying.GroupBuying;
import com.example.livingbymyselfserver.groupBuying.GroupBuyingServiceImpl;
import com.example.livingbymyselfserver.groupBuying.enums.GroupBuyingStatusEnum;
import com.example.livingbymyselfserver.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationUsersServiceImpl implements ApplicationUsersService {

  private final ApplicationUsersRepository applicationUsersRepository;
  private final GroupBuyingServiceImpl groupBuyingService;
  private final RedisUtil redisUtil;

  @Override
  @Transactional
  public ApiResponseDto createApplication(User user, Long groupBuyingId) {

    GroupBuying groupBuying = groupBuyingService.findGroupBuying(groupBuyingId);
    if (applicationUsersRepository.existsByGroupBuyingAndUser(groupBuying, user))
      throw new IllegalArgumentException("이미 신청한 공고입니다.");
    if (groupBuying.getEnumStatus() == GroupBuyingStatusEnum.DEADLINE)
      throw new IllegalArgumentException("마감된 공고입니다.");
    //유저에 캐쉬 추가한 뒤 캐쉬 확인 차감작업

    if (user.getCash() < groupBuying.getPerUserPrice()) {
      throw new IllegalArgumentException("캐쉬가 부족합니다. 충전해주세요!");
    }
    if (groupBuying.getAppUsers().size() + 1 > groupBuying.getMaxUser()) {
      throw new IllegalArgumentException("인원이 가득 찼습니다.");
    }
    Long cash = user.getCash()-Long.valueOf(groupBuying.getPerUserPrice());
    user.setCash(cash); //유저 캐쉬 값이 DB에 적용되지 않는 에러

    ApplicationUsers applicationUsers = new ApplicationUsers(user, groupBuying);  //신청

    applicationUsersRepository.save(applicationUsers);

    return new ApiResponseDto("공동구매 신청완료", 200);
  }

  @Override
  @Transactional
  public ApiResponseDto deleteApplication(User user, Long groupBuyingId) {
    GroupBuying groupBuying = groupBuyingService.findGroupBuying(groupBuyingId);

    if (!applicationUsersRepository.existsByGroupBuyingAndUser(groupBuying, user)) {
      throw new IllegalArgumentException("해당 공고를 신청한 유저가 아닙니다.");
    }
    redisUtil.delete("GroupBuying:"+user.getId()+groupBuyingId);
    //삭제시 redis내 같은 키값 조회수 데이터도 삭제해주기
    ApplicationUsers applicationUsers = applicationUsersRepository.findByGroupBuyingAndUser(groupBuying, user);
    user.setCash(user.getCash()+groupBuying.getPerUserPrice());
    applicationUsersRepository.delete(applicationUsers);

    return new ApiResponseDto("공고 지원 취소", 200);
  }
}

