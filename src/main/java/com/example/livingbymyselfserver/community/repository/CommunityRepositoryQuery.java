package com.example.livingbymyselfserver.community.repository;

import com.example.livingbymyselfserver.community.Community;
import com.example.livingbymyselfserver.community.CommunityCategoryEnum;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommunityRepositoryQuery {

  Page<Community> searchItemList(Pageable pageable, String keyword, CommunityCategoryEnum category, String sort);

  BooleanExpression containsKeyword(String keyword);
}
