package com.example.livingbymyselfserver.chat.entity.dto;

import com.example.livingbymyselfserver.chat.entity.ChatRoom;
import com.example.livingbymyselfserver.user.User;
import java.util.Set;
import lombok.Getter;

@Getter
public class ChatRoomListResponseDto {
  private Long id;
  Set<User> users;

  public ChatRoomListResponseDto(ChatRoom chatRoom) {
    this.id = chatRoom.getId();
    this.users = chatRoom.getUsers();
  }
}
