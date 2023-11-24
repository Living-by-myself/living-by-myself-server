package com.example.livingbymyselfserver.chat.entity.dto;

import com.example.livingbymyselfserver.chat.entity.Chat;
import lombok.Getter;


@Getter
public class ChatMessageResponseDto {

  private Long chatId;
  private String msg;
  private String localTime;
  private ChatResponseDto responseDto;


  public ChatMessageResponseDto(Chat chat) {
    this.chatId = chat.getId();
    this.msg = chat.getMessage();
    this.localTime = chat.getCreatedAtAsString();
    this.responseDto = new ChatResponseDto(chat.getUser(),chat.getUser().getFileUrls());
  }
}