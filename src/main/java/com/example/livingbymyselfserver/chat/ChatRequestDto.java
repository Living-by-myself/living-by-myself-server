package com.example.livingbymyselfserver.chat;

import com.example.livingbymyselfserver.chat.entity.ChatRoom;
import com.example.livingbymyselfserver.user.User;
import lombok.Getter;

@Getter
public class ChatRequestDto {
  private Long id;

  private String message;

  private ChatRoom chatRoom;

  private User user;

}
