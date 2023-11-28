package com.example.livingbymyselfserver.chat;

import com.example.livingbymyselfserver.chat.entity.Chat;
import com.example.livingbymyselfserver.chat.entity.dto.ChatRequestDto;
import com.example.livingbymyselfserver.chat.entity.dto.ChatRoomRequestDto;
import com.example.livingbymyselfserver.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "채팅API")
@RequiredArgsConstructor
public class ChatController {

  private final ChatServiceImpl chatService;

  @Operation(summary = "채팅방 접속 겸 채팅 보내")
  @MessageMapping("/{roomId}")
  @SendTo("/topic/room/{roomId}")
  public Chat test(@DestinationVariable Long roomId, @RequestBody ChatRequestDto requestDto) {

    return chatService.createChat(roomId, requestDto.getUserId(), requestDto.getMessage());
  }

  @Operation(summary = "채팅방 생성")
  @PostMapping("/room")
  public ResponseEntity<Long> createChatRoom(@AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody ChatRoomRequestDto requestDto) {

    List<Long> userIdList = requestDto.getUsersId().stream()
        .map(Long::valueOf) // Convert each String to Long
        .collect(Collectors.toList());
    Long chatRoomId = chatService.createChatRoom(userDetails.getUser().getId(), userIdList,requestDto.getTitle());

    return ResponseEntity.status(HttpStatus.CREATED).body(chatRoomId);
  }
}
