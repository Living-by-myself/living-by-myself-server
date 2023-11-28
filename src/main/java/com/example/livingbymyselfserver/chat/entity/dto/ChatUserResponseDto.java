package com.example.livingbymyselfserver.chat.entity.dto;

import com.example.livingbymyselfserver.attachment.entity.AttachmentUserUrl;
import com.example.livingbymyselfserver.user.User;
import lombok.Getter;

@Getter
public class ChatUserResponseDto {
  private final Long id;
  private Long groupBuyingRoomId;
  private final String nickname;
  private String profileImage;
  private String address;


  public ChatUserResponseDto(User user, Long groupBuyingRoomId, AttachmentUserUrl attachmentUserUrl) {
    this.id = user.getId();
    this.nickname = user.getNickname();
    this.profileImage = attachmentUserUrl.getFileName();
    this.address = user.getAddress();
    this.groupBuyingRoomId = groupBuyingRoomId;
  }

  public ChatUserResponseDto(User user,Long groupBuyingRoomId) {
    this.id = user.getId();
    this.nickname = user.getNickname();
    this.address = user.getAddress();
    this.groupBuyingRoomId = groupBuyingRoomId;
  }
}