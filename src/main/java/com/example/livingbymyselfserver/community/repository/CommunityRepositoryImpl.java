package com.example.livingbymyselfserver.community.repository;

import com.example.livingbymyselfserver.community.Community;
import com.example.livingbymyselfserver.community.CommunityCategoryEnum;
import com.example.livingbymyselfserver.community.QCommunity;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommunityRepositoryImpl implements CommunityRepositoryQuery{
  private final JPAQueryFactory jpaQueryFactory;
  QCommunity qCommunity = QCommunity.community;


  @Override
  public Page<Community> searchItemList(Pageable pageable, String keyword,
      CommunityCategoryEnum category, String sort) {
    QueryResults<Community> results = jpaQueryFactory
        .selectFrom(qCommunity)
        .where(containsKeyword(keyword),categoryEq(category)).orderBy(sort.equals("desc")? qCommunity.createdAt.desc():qCommunity.createdAt.asc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetchResults();

    return new PageImpl<>(results.getResults(), pageable, results.getTotal());
  }
  @Override
  public BooleanExpression containsKeyword(String keyword) {
    if (keyword == null || keyword.trim().isEmpty()) {
      return null;
    }
    return qCommunity.title.containsIgnoreCase(keyword);
  }
  private BooleanExpression categoryEq(CommunityCategoryEnum category) {
    if (category == null) {
      return null;
    }
    return QCommunity.community.category.eq(category);
  }

}
