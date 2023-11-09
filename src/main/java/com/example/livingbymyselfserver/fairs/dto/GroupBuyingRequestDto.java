package com.example.livingbymyselfserver.fairs.dto;

import com.example.livingbymyselfserver.fairs.GroupBuyingCategoryEnum;
import com.example.livingbymyselfserver.fairs.GroupBuyingShareEnum;
import lombok.Getter;

@Getter
public class GroupBuyingRequestDto {
  private String title;
  private String description;
  private String itemLink;
  private Integer maxUser;
  private Integer perUserPrice;
  private GroupBuyingShareEnum enumShare;
  private GroupBuyingCategoryEnum enumCategory;
  private String address;
  private String beobJeongDong;
  private double lat;
  private double lng;
}
