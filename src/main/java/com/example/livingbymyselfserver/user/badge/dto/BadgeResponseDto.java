package com.example.livingbymyselfserver.user.badge.dto;

import com.example.livingbymyselfserver.user.badge.Badge;
import com.example.livingbymyselfserver.user.badge.BadgeEnum;
import lombok.Getter;

@Getter
public class BadgeResponseDto {
    private final BadgeEnum type;

    public BadgeResponseDto(Badge badge) {
        this.type = badge.getBadgeEnum();
    }
}
