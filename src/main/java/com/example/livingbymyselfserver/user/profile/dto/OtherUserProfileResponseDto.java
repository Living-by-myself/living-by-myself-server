package com.example.livingbymyselfserver.user.profile.dto;

import com.example.livingbymyselfserver.attachment.entity.AttachmentUserUrl;
import com.example.livingbymyselfserver.user.User;
import lombok.Getter;

@Getter
public class OtherUserProfileResponseDto {
  private String nickname;
  private String profileImage;
  private Long level;
  private String address;

  public OtherUserProfileResponseDto(User user, AttachmentUserUrl attachmentUserUrl) {
    this.nickname = user.getNickname();
    this.profileImage = attachmentUserUrl.getFileName();
    this.level = user.getLevel();
    this.address = user.getAddress();
  }

  public OtherUserProfileResponseDto(User user) {
    this.nickname = user.getNickname();
    this.level = user.getLevel();
    this.address = user.getAddress();
  }
}
