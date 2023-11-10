package com.example.livingbymyselfserver.user.profile.dto;

import com.example.livingbymyselfserver.user.User;
import lombok.Getter;

@Getter
public class ProfileResponseDto {
    private String nickname;
    private String profileImage;
    private Long level;
    private String address;
    private Long cash;

  public ProfileResponseDto(User user) {
    this.nickname = user.getNickname();
//    this.profileImage =
    this.level = user.getLevel();
    this.address = user.getAddress();
    this.cash = user.getCash();
  }
}
