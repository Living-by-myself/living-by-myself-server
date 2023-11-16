package com.example.livingbymyselfserver.groupBuying.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class GroupBuyingListResponseDto {
  List<GroupBuyingResponseDto> groupBuyingResponseDtoList;
  Long len;

  public GroupBuyingListResponseDto(List<GroupBuyingResponseDto> groupBuyingResponseDtoList, Long len)
  {
    this.groupBuyingResponseDtoList = groupBuyingResponseDtoList;
    this.len = len;
  }
}
