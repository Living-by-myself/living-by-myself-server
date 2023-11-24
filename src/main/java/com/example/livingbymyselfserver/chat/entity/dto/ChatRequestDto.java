package com.example.livingbymyselfserver.chat.entity.dto;

import lombok.Getter;

@Getter
public class ChatRequestDto {
  private Long userId;

  private String message;
}
