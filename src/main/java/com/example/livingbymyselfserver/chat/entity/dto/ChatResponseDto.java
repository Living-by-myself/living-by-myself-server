package com.example.livingbymyselfserver.chat.entity.dto;

import com.example.livingbymyselfserver.user.User;
import lombok.Getter;

@Getter
public class ChatResponseDto {

  private Long id;
  private String username;
  private String nickname;
  private String fileUrls;

  public ChatResponseDto(User user, String fileUrls) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.nickname = user.getNickname();
    this.fileUrls = fileUrls;
  }
}