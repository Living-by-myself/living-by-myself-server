package com.example.livingbymyselfserver.groupBuying.repository;

import com.example.livingbymyselfserver.groupBuying.GroupBuying;
import com.example.livingbymyselfserver.groupBuying.enums.GroupBuyingCategoryEnum;
import com.example.livingbymyselfserver.groupBuying.enums.GroupBuyingShareEnum;
import com.example.livingbymyselfserver.groupBuying.enums.GroupBuyingStatusEnum;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GroupBuyingRepositoryQuery   {
  Page<GroupBuying> searchItemList(Pageable pageable, String keyword, GroupBuyingCategoryEnum category,
      GroupBuyingShareEnum shareEnum, GroupBuyingStatusEnum statusEnum,String beobJeongDong,String sort);

  List<GroupBuying> findCategory(GroupBuyingCategoryEnum categoryEnum, Pageable pageable);

  BooleanExpression containsKeyword(String keyword);

}
