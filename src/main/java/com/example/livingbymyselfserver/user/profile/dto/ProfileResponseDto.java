package com.example.livingbymyselfserver.user.profile.dto;

import com.example.livingbymyselfserver.attachment.entity.AttachmentUserUrl;
import com.example.livingbymyselfserver.user.User;
import lombok.Getter;

@Getter
public class ProfileResponseDto {

  private Long userId;
  private String nickname;
  private String profileImage;
  private Long level;
  private String address;
  private Long cash;

  public ProfileResponseDto(User user, AttachmentUserUrl attachmentUserUrl) {
    this.userId = user.getId();
    this.nickname = user.getNickname();
    this.profileImage = attachmentUserUrl.getFileName();
    this.level = user.getLevel();
    this.address = user.getAddress();
    this.cash = user.getCash();
  }

  public ProfileResponseDto(User user) {
      this.userId = user.getId();
      this.nickname = user.getNickname();
      this.level = user.getLevel();
      this.address = user.getAddress();
      this.cash = user.getCash();
  }
}
