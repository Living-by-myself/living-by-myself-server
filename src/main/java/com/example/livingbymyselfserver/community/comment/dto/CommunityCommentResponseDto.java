package com.example.livingbymyselfserver.community.comment.dto;

import com.example.livingbymyselfserver.community.comment.CommunityComment;
import lombok.Getter;

@Getter
public class CommunityCommentResponseDto {
    private Long id;
    private String description;
    private String nickName;
    private int likeCnt;

    public CommunityCommentResponseDto(CommunityComment comment) {
        this.id = comment.getId();
        this.description = comment.getDescription();
        this.nickName = comment.getUser().getNickname();
        this.likeCnt = comment.getCommunityCommentLikeList().size();
    }
}
