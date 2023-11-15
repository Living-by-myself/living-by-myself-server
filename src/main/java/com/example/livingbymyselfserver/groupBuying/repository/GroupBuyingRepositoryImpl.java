package com.example.livingbymyselfserver.groupBuying.repository;

import com.example.livingbymyselfserver.groupBuying.GroupBuying;
import com.example.livingbymyselfserver.groupBuying.QGroupBuying;
import com.example.livingbymyselfserver.groupBuying.enums.GroupBuyingCategoryEnum;
import com.example.livingbymyselfserver.groupBuying.enums.GroupBuyingShareEnum;
import com.example.livingbymyselfserver.groupBuying.enums.GroupBuyingStatusEnum;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GroupBuyingRepositoryImpl implements GroupBuyingRepositoryQuery{

  private final JPAQueryFactory jpaQueryFactory;
   QGroupBuying qGroupBuying = QGroupBuying.groupBuying;

  public List<GroupBuying> findCategory(GroupBuyingCategoryEnum categoryEnum, Pageable pageable) {
    BooleanExpression categoryPredicate = qGroupBuying.enumCategory.eq(categoryEnum);

    return jpaQueryFactory
        .selectFrom(qGroupBuying)
        .where(categoryPredicate)
        .fetch();
  }

  @Override
  public Page<GroupBuying> searchItemList(Pageable pageable, GroupBuyingCategoryEnum category,
      GroupBuyingShareEnum shareEnum, GroupBuyingStatusEnum statusEnum, String beobJeongDong) {
//    BooleanExpression categoryPredicate = categoryEq(category)
//        .and(statusEq(statusEnum))
//        .and(shareEq(shareEnum))
//        .and(addressEq(beobJeongDong));

    QueryResults<GroupBuying> results = jpaQueryFactory
        .selectFrom(qGroupBuying)
        .where(categoryEq(category), statusEq(statusEnum),shareEq(shareEnum),addressEq(beobJeongDong))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetchResults();

    return new PageImpl<>(results.getResults(), pageable, results.getTotal());
  }

  @Override
  public Long searchGroupBuyingListSize(GroupBuyingCategoryEnum category,
      GroupBuyingShareEnum enumShare, GroupBuyingStatusEnum status, String beobJeongDong) {
//    BooleanExpression categoryPredicate = categoryEq(category)
//        .and(statusEq(status))
//        .and(shareEq(enumShare))
//        .and(addressEq(beobJeongDong));

    return jpaQueryFactory.selectFrom(qGroupBuying)
        .where(categoryEq(category),statusEq(status),shareEq(enumShare),addressEq(beobJeongDong))
        .fetchCount();
  }

  private BooleanExpression addressEq(String beobJeongDong) {
    if (beobJeongDong == null) {
      return null;
    }
    return QGroupBuying.groupBuying.beobJeongDong.eq(beobJeongDong);
  }
  private BooleanExpression categoryEq(GroupBuyingCategoryEnum category) {
    if (category == null) {
      return null;
    }
    return QGroupBuying.groupBuying.enumCategory.eq(category);
  }
  private BooleanExpression statusEq(GroupBuyingStatusEnum category) {
    if (category == null) {
      return null;
    }
    return QGroupBuying.groupBuying.enumStatus.eq(category);
  }
  private BooleanExpression shareEq(GroupBuyingShareEnum category) {
    if (category == null) {
      return null;
    }
    return QGroupBuying.groupBuying.enumShare.eq(category);
  }
}
