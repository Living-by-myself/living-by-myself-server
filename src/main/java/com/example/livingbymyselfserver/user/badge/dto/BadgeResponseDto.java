package com.example.livingbymyselfserver.user.badge.dto;

import com.example.livingbymyselfserver.user.badge.Badge;
import com.example.livingbymyselfserver.user.badge.BadgeEnum;
import lombok.Getter;

@Getter
public class BadgeResponseDto {
    private final Long id;
    private final BadgeEnum type;

    public BadgeResponseDto(Badge badge) {
        this.id = badge.getId();
        this.type = badge.getBadgeEnum();
    }
}
