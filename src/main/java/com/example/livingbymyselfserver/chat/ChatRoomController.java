package com.example.livingbymyselfserver.chat;

import com.example.livingbymyselfserver.chat.entity.dto.ChatMessageResponseDto;
import com.example.livingbymyselfserver.chat.entity.dto.ChatRoomListResponseDto;
import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.security.UserDetailsImpl;
import com.example.livingbymyselfserver.user.dto.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home/chats")
@Tag(name = "채팅방 조회 및 삭제")
@RequiredArgsConstructor
public class ChatRoomController {

  private final ChatServiceImpl chatService;

  @Operation(summary = "채팅방 메세지 조회")
  @GetMapping("/room/{roomNo}")
  public List<ChatMessageResponseDto> getChatMessages(@PathVariable Long roomNo) {
    return chatService.getChatMessages(roomNo);
  }

  @Operation(summary = "채팅방 삭제")
  @DeleteMapping("/room/{roomNo}")
  public ApiResponseDto deleteChatMessages(@PathVariable Long roomNo) {
    return chatService.deleteChatRoom(roomNo);
  }

  @Operation(summary = "채팅방 조회")
  @GetMapping("/rooms")
  public List<ChatRoomListResponseDto> getChatRooms(
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return chatService.getChatRooms(userDetails.getUser());
  }
  //사진 닉네임 주소 유저id
  @Operation(summary = "채팅방 유저리스트 조회")
  @GetMapping("/rooms/{roomId}/users")
  public List<UserResponseDto> getChatRoomUsers(
      @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long roomId) {
    return chatService.getChatRoomUsers(userDetails.getUser(),roomId);
  }
}