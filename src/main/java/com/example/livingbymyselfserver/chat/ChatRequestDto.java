package com.example.livingbymyselfserver.chat;

import lombok.Getter;

@Getter
public class ChatRequestDto {
  private Long userId;

  private String message;
}
