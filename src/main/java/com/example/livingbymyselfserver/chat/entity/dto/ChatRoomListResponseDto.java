package com.example.livingbymyselfserver.chat.entity.dto;

import com.example.livingbymyselfserver.chat.entity.Chat;
import com.example.livingbymyselfserver.chat.entity.ChatRoom;
import com.example.livingbymyselfserver.user.User;
import java.util.Set;
import lombok.Getter;

@Getter
public class ChatRoomListResponseDto {
  private Long id;
  private Set<User> users;
  private String lastChatMsg;
  private String lastChatTime;

  public ChatRoomListResponseDto(ChatRoom chatRoom) {
    this.id = chatRoom.getId();
    this.users = chatRoom.getUsers();
    if(chatRoom.getChat().size()>0){
      this.lastChatMsg = chatRoom.getChat().get(chatRoom.getChat().size()-1).getMessage();
      this.lastChatTime = chatRoom.getChat().get(chatRoom.getChat().size()-1).getCreatedAtAsString();
    }
  }
}
