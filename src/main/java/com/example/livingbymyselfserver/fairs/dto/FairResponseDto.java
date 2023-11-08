package com.example.livingbymyselfserver.fairs.dto;

import com.example.livingbymyselfserver.fairs.Fair;
import com.example.livingbymyselfserver.fairs.FairShareEnum;
import lombok.Getter;

@Getter
public class FairResponseDto {
    String title;
    String description;
    String itemLink;
    Integer maxUser;
    Integer current_user_count;
    //MultipartFile multipartFile;
    Integer perUserPrice;
    FairShareEnum enumShare;
    int viewCnt;
    String address;
    String beobJeongDong;
    double lat;
    double lng;
    public FairResponseDto(Fair fair){
        this.title = fair.getTitle();
        this.description = fair.getDescription();
        this.itemLink = fair.getItemLink();
        this.maxUser = fair.getMaxUser();
        this.perUserPrice = fair.getPerUserPrice();
        this.address = fair.getAddress();
        this.enumShare = fair.getEnumShare();
        this.viewCnt = fair.getViewCnt();
        this.beobJeongDong = fair.getBeobJeongDong();
        this.lat = fair.getLat();
        this.lng =fair.getLng();
    }
}
