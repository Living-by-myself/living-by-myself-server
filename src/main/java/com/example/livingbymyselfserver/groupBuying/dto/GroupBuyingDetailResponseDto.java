package com.example.livingbymyselfserver.groupBuying.dto;

import com.example.livingbymyselfserver.attachment.entity.AttachmentGroupBuyingUrl;
import com.example.livingbymyselfserver.groupBuying.GroupBuying;
import com.example.livingbymyselfserver.groupBuying.enums.GroupBuyingShareEnum;
import com.example.livingbymyselfserver.user.User;
import java.util.List;
import lombok.Getter;

@Getter
public class GroupBuyingDetailResponseDto {
    private final Long id;
    private final String title;
    private final String description;
    private final String itemLink;
    private final Integer maxUser;
    private final Integer currentUserCount;
    private final String createdAt;
    private String fileUrls;
    private final Integer perUserPrice;
    private final GroupBuyingShareEnum enumShare;
    private final double viewCnt;
    private final String address;
    private final String beobJeongDong;
    private final double lat;
    private final double lng;
    private final List<User> users;
    private final Integer likeCount;
    private String enumStatus;
    private boolean isPickLike;

    public GroupBuyingDetailResponseDto(GroupBuying groupBuying, double viewCnt, List<User> users,Integer likeCount, boolean isPickLike){
        this.id = groupBuying.getId();
        this.viewCnt = (int)viewCnt;
        this.title = groupBuying.getTitle();
        this.description = groupBuying.getDescription();
        this.enumStatus = groupBuying.getEnumStatus().toString();
        this.itemLink = groupBuying.getItemLink();
        this.maxUser = groupBuying.getMaxUser();
        this.currentUserCount = users.size();
        this.perUserPrice = groupBuying.getPerUserPrice();
        this.address = groupBuying.getAddress();
        this.enumShare = groupBuying.getEnumShare();
        this.beobJeongDong = groupBuying.getBeobJeongDong();
        this.lat = groupBuying.getLat();
        this.lng = groupBuying.getLng();
        this.createdAt = groupBuying.getCreatedAtAsString();
        this.users = users;
        this.likeCount = likeCount;
        this.isPickLike = isPickLike;
    }

    public GroupBuyingDetailResponseDto(GroupBuying groupBuying, AttachmentGroupBuyingUrl attachmentGroupBuyingUrl, double viewCnt, List<User> users, Integer likeCount, boolean isPickLike){
        this.id = groupBuying.getId();
        this.viewCnt = (int)viewCnt;
        this.title = groupBuying.getTitle();
        this.description = groupBuying.getDescription();
        this.itemLink = groupBuying.getItemLink();
        this.maxUser = groupBuying.getMaxUser();
        this.enumStatus = groupBuying.getEnumStatus().toString();
        this.currentUserCount = users.size();
        this.perUserPrice = groupBuying.getPerUserPrice();
        this.address = groupBuying.getAddress();
        this.enumShare = groupBuying.getEnumShare();
        this.beobJeongDong = groupBuying.getBeobJeongDong();
        this.lat = groupBuying.getLat();
        this.lng = groupBuying.getLng();
        this.fileUrls = attachmentGroupBuyingUrl.getFileName();
        this.createdAt = groupBuying.getCreatedAtAsString();
        this.users = users;
        this.likeCount = likeCount;
        this.isPickLike = isPickLike;
    }
}
