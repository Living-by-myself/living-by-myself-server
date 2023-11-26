package com.example.livingbymyselfserver.chat.entity.dto;

import com.example.livingbymyselfserver.chat.entity.ChatRoom;
import com.example.livingbymyselfserver.user.User;
import java.util.Set;
import lombok.Getter;

@Getter
public class ChatRoomListResponseDto {
  private Long id;
  private Integer userCount;
  private String lastChatMsg;
  private String lastChatTime;

  public ChatRoomListResponseDto(ChatRoom chatRoom) {
    this.id = chatRoom.getId();
    this.userCount = chatRoom.getUsers().size();
    this.lastChatTime = chatRoom.getLastChatTime();
    this.lastChatMsg = chatRoom.getLastChatMsg();
  }
}
