package com.example.livingbymyselfserver.community.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class CommunityListResponseDto {

  List<CommunityResponseDto> communityResponseDtoList;
  Long len;
  public CommunityListResponseDto(List<CommunityResponseDto> communityResponseDtoList, Long len){
    this.communityResponseDtoList = communityResponseDtoList;
    this.len = len;
  }
}
