package com.example.livingbymyselfserver.chat;

import com.example.livingbymyselfserver.chat.entity.Chat;
import com.example.livingbymyselfserver.chat.entity.ChatRoom;
import com.example.livingbymyselfserver.chat.entity.dto.ChatMessageResponseDto;
import com.example.livingbymyselfserver.chat.entity.dto.ChatRoomListResponseDto;
import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.user.User;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface ChatService {
  /**
   * 채팅방 조회
   *
   * @param roomNo
   * @return
   */
  List<ChatMessageResponseDto> getChatMessages(Long roomNo);

  /**
   * 채팅 방 삭제
   *
   * @param roomNo
   * @return
   */
  ApiResponseDto deleteChatRoom(Long roomNo);

  /**
   * 채팅 방 생성
   *
   * @param userId
   * @param user
   * @return
   */
  Long createChatRoom(Long userId,List<Long> userIdList, String title);

  /**
   * 채팅 메세지 생성
   *
   * @param roomNo
   * @param userId
   * @param msg
   * @return
   */
  Chat createChat(Long roomNo, Long userId, String msg);

  /**
   * 채팅 방 리스트 조회
   *
   * @param user
   * @return
   */
  List<ChatRoomListResponseDto> getChatRooms(User user);

  ChatRoom getRoom(Long roomNo);
}
