package com.example.livingbymyselfserver.chat;

import com.example.livingbymyselfserver.chat.entity.Chat;
import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
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
    return chatService.createChat(roomId, requestDto.getUser().getId(), requestDto.getMessage());
  }

  @Operation(summary = "채팅방 생성")
  @PostMapping("/room/user")
  public ApiResponseDto createChatRoom(@AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody List<String> usersId) {
    List<Long> userIdList = usersId.stream()
        .map(Long::valueOf) // Convert each String to Long
        .collect(Collectors.toList());
    return chatService.createChatRoom(userDetails.getUser().getId(), userIdList);
  }
}
