package com.example.livingbymyselfserver.groupBuying.dto;

import com.example.livingbymyselfserver.attachment.entity.AttachmentGroupBuyingUrl;
import com.example.livingbymyselfserver.groupBuying.GroupBuying;
import com.example.livingbymyselfserver.groupBuying.enums.GroupBuyingShareEnum;
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

    public GroupBuyingDetailResponseDto(GroupBuying groupBuying, double viewCnt){
        this.id = groupBuying.getId();
        this.viewCnt = (int)viewCnt;
        this.title = groupBuying.getTitle();
        this.description = groupBuying.getDescription();

        this.itemLink = groupBuying.getItemLink();
        this.maxUser = groupBuying.getMaxUser();
        this.currentUserCount = groupBuying.getAppUsers().size();
        this.perUserPrice = groupBuying.getPerUserPrice();
        this.address = groupBuying.getAddress();
        this.enumShare = groupBuying.getEnumShare();
        this.beobJeongDong = groupBuying.getBeobJeongDong();
        this.lat = groupBuying.getLat();
        this.lng = groupBuying.getLng();
        this.createdAt = groupBuying.getCreatedAtAsString();
    }

    public GroupBuyingDetailResponseDto(GroupBuying groupBuying, AttachmentGroupBuyingUrl attachmentGroupBuyingUrl, double viewCnt){
        this.id = groupBuying.getId();
        this.viewCnt = (int)viewCnt;
        this.title = groupBuying.getTitle();
        this.description = groupBuying.getDescription();
        this.itemLink = groupBuying.getItemLink();
        this.maxUser = groupBuying.getMaxUser();
        this.currentUserCount = groupBuying.getAppUsers().size();
        this.perUserPrice = groupBuying.getPerUserPrice();
        this.address = groupBuying.getAddress();
        this.enumShare = groupBuying.getEnumShare();
        this.beobJeongDong = groupBuying.getBeobJeongDong();
        this.lat = groupBuying.getLat();
        this.lng = groupBuying.getLng();
        this.fileUrls = attachmentGroupBuyingUrl.getFileName();
        this.createdAt = groupBuying.getCreatedAtAsString();
    }
}
