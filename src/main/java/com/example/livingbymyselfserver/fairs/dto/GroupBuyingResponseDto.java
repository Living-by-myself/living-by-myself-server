package com.example.livingbymyselfserver.fairs.dto;

import com.example.livingbymyselfserver.fairs.GroupBuying;
import com.example.livingbymyselfserver.fairs.GroupBuyingShareEnum;
import lombok.Getter;

@Getter
public class GroupBuyingResponseDto {
    private final String title;
    private final String description;
    private final String itemLink;
    private final Integer maxUser;
    private final Integer current_user_count;
    //MultipartFile multipartFile;
    private final Integer perUserPrice;
    private final GroupBuyingShareEnum enumShare;
    private final int viewCnt;
    private final String address;
    private final String beobJeongDong;
    private final double lat;
    private final double lng;
    public GroupBuyingResponseDto(GroupBuying groupBuying){
        this.title = groupBuying.getTitle();
        this.description = groupBuying.getDescription();
        this.itemLink = groupBuying.getItemLink();
        this.maxUser = groupBuying.getMaxUser();
        this.current_user_count = groupBuying.getAppUsers().size();
        this.perUserPrice = groupBuying.getPerUserPrice();
        this.address = groupBuying.getAddress();
        this.enumShare = groupBuying.getEnumShare();
        this.viewCnt = groupBuying.getViewCnt();
        this.beobJeongDong = groupBuying.getBeobJeongDong();
        this.lat = groupBuying.getLat();
        this.lng = groupBuying.getLng();
    }
}
