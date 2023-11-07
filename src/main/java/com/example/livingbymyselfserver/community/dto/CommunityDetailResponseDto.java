package com.example.livingbymyselfserver.community.dto;

import com.example.livingbymyselfserver.community.Community;
import com.example.livingbymyselfserver.community.CommunityCategoryEnum;
import lombok.Getter;

@Getter
public class CommunityDetailResponseDto {
    private Long id;
    private int viewCnt;
    private String title;
    private String decription;
    private CommunityCategoryEnum category;
    private Long userId;
    private String userNickname;
    private String getCreatedAtAsString;
    private int commentCnt;
    private int likeCnt;

    public CommunityDetailResponseDto(Community community) {
        this.id = community.getId();
        this.viewCnt = community.getViewCnt();
        this.title = community.getTitle();
        this.decription = community.getDescription();
        this.category = community.getCategory();
        this.getCreatedAtAsString = community.getCreatedAtAsString();
        this.userId = community.getUser().getId();
        this.userNickname = community.getUser().getNickname();
        this.commentCnt = community.getCommentList().size();
        this.likeCnt = community.getLikeList().size();
    }
}
