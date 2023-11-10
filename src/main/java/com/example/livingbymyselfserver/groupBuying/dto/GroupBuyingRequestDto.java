package com.example.livingbymyselfserver.groupBuying.dto;

import com.example.livingbymyselfserver.groupBuying.GroupBuyingCategoryEnum;
import com.example.livingbymyselfserver.groupBuying.GroupBuyingShareEnum;
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
