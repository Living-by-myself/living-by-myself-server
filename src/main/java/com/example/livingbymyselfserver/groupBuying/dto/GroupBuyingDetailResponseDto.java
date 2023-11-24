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
    private final User host;
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
    private String status;

    public GroupBuyingDetailResponseDto(GroupBuying groupBuying, double viewCnt, List<User> users,Integer likeCount){
        this.id = groupBuying.getId();
        this.viewCnt = (int)viewCnt;
        this.host = groupBuying.getHost();
        this.title = groupBuying.getTitle();
        this.description = groupBuying.getDescription();
        this.status = groupBuying.getEnumStatus().toString();
        this.itemLink = groupBuying.getItemLink();
        this.maxUser = groupBuying.getMaxUser();
        this.currentUserCount = groupBuying.getAppUsers().size() + 1;
        this.perUserPrice = groupBuying.getPerUserPrice();
        this.address = groupBuying.getAddress();
        this.enumShare = groupBuying.getEnumShare();
        this.beobJeongDong = groupBuying.getBeobJeongDong();
        this.lat = groupBuying.getLat();
        this.lng = groupBuying.getLng();
        this.createdAt = groupBuying.getCreatedAtAsString();
        this.users = users;
        this.likeCount = likeCount;
    }

    public GroupBuyingDetailResponseDto(GroupBuying groupBuying, AttachmentGroupBuyingUrl attachmentGroupBuyingUrl, double viewCnt, List<User> users, Integer likeCount){
        this.id = groupBuying.getId();
        this.viewCnt = (int)viewCnt;
        this.host = groupBuying.getHost();
        this.title = groupBuying.getTitle();
        this.description = groupBuying.getDescription();
        this.itemLink = groupBuying.getItemLink();
        this.maxUser = groupBuying.getMaxUser();
        this.status = groupBuying.getEnumStatus().toString();
        this.currentUserCount = groupBuying.getAppUsers().size()+1;
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
    }
}
