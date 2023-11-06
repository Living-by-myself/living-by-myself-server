package com.example.livingbymyselfserver.community.dto;

import com.example.livingbymyselfserver.community.CommunityCategoryEnum;
import lombok.Getter;

@Getter
public class CommunityRequestDto {
    private Long id;
    private String title;
    private String description;
    private CommunityCategoryEnum category;
}
