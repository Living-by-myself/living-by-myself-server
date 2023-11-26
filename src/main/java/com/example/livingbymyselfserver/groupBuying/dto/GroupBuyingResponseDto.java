package com.example.livingbymyselfserver.groupBuying.dto;

import com.example.livingbymyselfserver.attachment.entity.AttachmentGroupBuyingUrl;
import com.example.livingbymyselfserver.groupBuying.GroupBuying;
import com.example.livingbymyselfserver.groupBuying.enums.GroupBuyingShareEnum;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class GroupBuyingResponseDto {
    private final Long id;
    private final String title;
    private final Integer maxUser;
    private final Integer current_user_count;
    private String fileUrls;
    private final Integer perUserPrice;
    private final GroupBuyingShareEnum enumShare;
    private final String enumStatus;
    private final int viewCnt;
    private final String address;
    private final String beobJeongDong;
    private final LocalDateTime createAt;
    private final LocalDateTime modifiedAt;
    private  boolean isPickLike;
    public GroupBuyingResponseDto(GroupBuying groupBuying, AttachmentGroupBuyingUrl attachmentGroupBuyingUrl, double viewCnt, boolean isPickLike){
        this.id = groupBuying.getId();
        this.title = groupBuying.getTitle();
        this.maxUser = groupBuying.getMaxUser();
        this.current_user_count = groupBuying.getAppUsers().size();
        this.perUserPrice = groupBuying.getPerUserPrice();
        this.address = groupBuying.getAddress();
        this.enumShare = groupBuying.getEnumShare();
        this.enumStatus = groupBuying.getEnumStatus().toString();
        this.viewCnt = (int)viewCnt;
        this.beobJeongDong = groupBuying.getBeobJeongDong();
        this.fileUrls = attachmentGroupBuyingUrl.getFileName().split(",")[0];
        this.createAt = groupBuying.getCreatedAt();
        this.modifiedAt = groupBuying.getModifiedAt();
        this.isPickLike = isPickLike;
    }
    public GroupBuyingResponseDto(GroupBuying groupBuying,double viewCnt, boolean isPickLike){
        this.id = groupBuying.getId();
        this.title = groupBuying.getTitle();
        this.maxUser = groupBuying.getMaxUser();
        this.current_user_count = groupBuying.getAppUsers().size();
        this.perUserPrice = groupBuying.getPerUserPrice();
        this.address = groupBuying.getAddress();
        this.enumShare = groupBuying.getEnumShare();
        this.enumStatus = groupBuying.getEnumStatus().toString();
        this.viewCnt = (int)viewCnt;
        this.beobJeongDong = groupBuying.getBeobJeongDong();
        this.createAt = groupBuying.getCreatedAt();
        this.modifiedAt = groupBuying.getModifiedAt();
        this.isPickLike = isPickLike;
    }
}
