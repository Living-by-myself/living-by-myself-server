package com.example.livingbymyselfserver.chat.entity.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class ChatRoomRequestDto {

  List<String> usersId;
  String title;
}
