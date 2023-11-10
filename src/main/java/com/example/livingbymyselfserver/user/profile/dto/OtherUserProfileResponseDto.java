package com.example.livingbymyselfserver.user.profile.dto;

import com.example.livingbymyselfserver.user.User;
import lombok.Getter;

@Getter
public class OtherUserProfileResponseDto {
  private String nickname;
  private String profileImage;
  private Long level;
  private String address;


  public OtherUserProfileResponseDto(User user) {
    this.nickname = user.getNickname();
//    this.profileImage = user.get
    this.level = user.getLevel();
    this.address = user.getAddress();
  }
}
