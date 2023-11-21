package com.example.livingbymyselfserver.community.comment.dto;

import com.example.livingbymyselfserver.community.comment.CommunityComment;
import com.example.livingbymyselfserver.user.User;
import com.example.livingbymyselfserver.user.dto.UserResponseDto;
import lombok.Getter;

@Getter
public class CommunityCommentResponseDto {
    private final Long id;
    private final String description;
    private final int likeCnt;
    private final Boolean existsLike;
    private final String getCreatedAtAsString;
    private final String getModifiedAtAsString;
    private final UserResponseDto user;
    public CommunityCommentResponseDto(CommunityComment comment, Boolean existsLike, UserResponseDto userResponseDto) {
        this.id = comment.getId();
        this.description = comment.getDescription();
        this.likeCnt = comment.getCommunityCommentLikeList().size();
        this.existsLike = existsLike;
        this.getModifiedAtAsString = comment.getModifiedAtAsString();
        this.getCreatedAtAsString = comment.getCreatedAtAsString();
        this.user = userResponseDto;
    }
}
