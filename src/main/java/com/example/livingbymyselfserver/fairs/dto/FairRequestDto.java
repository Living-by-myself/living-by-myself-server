package com.example.livingbymyselfserver.fairs.dto;

import com.example.livingbymyselfserver.fairs.FairShareEnum;
import lombok.Getter;

@Getter
public class FairRequestDto {
  String title;
  String description;
  String itemLink;
  Integer maxUser;
  Integer perUserPrice;
  FairShareEnum enumShare;
  String address;
  double lat;
  double lng;
}
