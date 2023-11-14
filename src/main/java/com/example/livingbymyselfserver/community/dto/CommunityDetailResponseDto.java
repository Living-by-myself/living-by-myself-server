package com.example.livingbymyselfserver.community.dto;

import com.example.livingbymyselfserver.attachment.community.S3CommunityFileResponseDto;
import com.example.livingbymyselfserver.attachment.entity.AttachmentCommunityUrl;
import com.example.livingbymyselfserver.community.Community;
import com.example.livingbymyselfserver.community.CommunityCategoryEnum;
import lombok.Getter;

@Getter
public class CommunityDetailResponseDto {
    private final Long id;
    private final int viewCnt;
    private final String title;
    private final String description;
    private final CommunityCategoryEnum category;
    private final Long userId;
    private final String userNickname;
    private final String getCreatedAtAsString;
    private final int commentCnt;
    private final int likeCnt;
    private String fileUrls;

    public CommunityDetailResponseDto(Community community, AttachmentCommunityUrl attachmentCommunityUrl, double viewCnt) {
        this.id = community.getId();
        this.viewCnt = (int)viewCnt;
        this.title = community.getTitle();
        this.description = community.getDescription();
        this.category = community.getCategory();
        this.getCreatedAtAsString = community.getCreatedAtAsString();
        this.userId = community.getUser().getId();
        this.userNickname = community.getUser().getNickname();
        this.commentCnt = community.getCommentList().size();
        this.likeCnt = community.getLikeList().size();
        this.fileUrls = attachmentCommunityUrl.getFileName();
    }

    public CommunityDetailResponseDto(Community community, double viewCnt) {
        this.id = community.getId();
        this.viewCnt = (int)viewCnt;
        this.title = community.getTitle();
        this.description = community.getDescription();
        this.category = community.getCategory();
        this.getCreatedAtAsString = community.getCreatedAtAsString();
        this.userId = community.getUser().getId();
        this.userNickname = community.getUser().getNickname();
        this.commentCnt = community.getCommentList().size();
        this.likeCnt = community.getLikeList().size();
    }
}
