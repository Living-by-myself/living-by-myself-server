package com.example.livingbymyselfserver.chat;

import com.example.livingbymyselfserver.attachment.entity.AttachmentUserUrl;
import com.example.livingbymyselfserver.attachment.user.AttachmentUserUrlRepository;
import com.example.livingbymyselfserver.chat.entity.Chat;
import com.example.livingbymyselfserver.chat.entity.ChatRoom;
import com.example.livingbymyselfserver.chat.entity.dto.ChatMessageResponseDto;
import com.example.livingbymyselfserver.chat.entity.dto.ChatRoomListResponseDto;
import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.groupBuying.dto.GroupBuyingResponseDto;
import com.example.livingbymyselfserver.user.User;
import com.example.livingbymyselfserver.user.UserRepository;
import com.example.livingbymyselfserver.user.UserService;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService {

  private final ChatRoomRespository chatRoomRepository;
  private final ChatRepository chatRepository;
  private final AttachmentUserUrlRepository attachmentUserUrlRepository;
  private final UserService userService;
  private final UserRepository userRepository;

  @Override
  @Transactional
  public Chat createChat(Long roomNo, Long userId, String msg) {  //채팅메세지생성

    log.info("채팅 메세지 만들기 들어옴");
    ChatRoom chatRoom = getRoom(roomNo);
    log.info("채팅 메세지 만들기 채팅방 찾음");
    User user = userService.findUser(userId);
    log.info("채팅 메세지 만들기 유저 찾음");
    AttachmentUserUrl attachmentUserUrl = attachmentUserUrlRepository.findByUser(user);
    log.info("채팅 메세지 만들기 유저이미지 찾음");

    Chat chat = new Chat(msg,chatRoom, user, attachmentUserUrl.getFileName());
    log.info("채팅 메세지 만들기 채팅 생성완료");
    log.info("채팅 메세지 = "+chat.getMessage());
    chatRepository.save(chat);
    log.info("채팅 메세지 저장로직 지나옴");

    return chat;
  }

  @Override
  public Long createChatRoom(Long userId,List<Long> userIdList) {
    User user = userService.findUser(userId);

    if (userIdList.stream().anyMatch(id -> id.equals(userId))) {
      throw new IllegalArgumentException("자기자신과는 채팅할 수 없습니다.");
    }

//    if (findChatRoom != null) {
//      throw new IllegalArgumentException("생성되 있는 채팅방입니다");
//    }
    List<User> users = userRepository.findByIdIn(userIdList);

    ChatRoom chatRoom = new ChatRoom(user, users);
    chatRoomRepository.save(chatRoom);


    return chatRoom.getId();
  }

  @Override
  public ChatRoom getRoom(Long roomNo) {
    return chatRoomRepository.findById(roomNo).orElseThrow(() -> {
      throw new IllegalArgumentException("채팅방이 존재하지 않습니다.");
    });
  }

  @Override
  public List<ChatMessageResponseDto> getChatMessages(Long roomNo) {
    ChatRoom chatRoom = getRoom(roomNo);
    return chatRepository.findByChatRoom(chatRoom).stream().map(ChatMessageResponseDto::new)
        .toList();
  }

  @Override
  public ApiResponseDto deleteChatRoom(Long roomNo) {
    ChatRoom chatRoom = getRoom(roomNo);
    if (chatRoom != null) {
      // 챗 엔터티의 chatRoom 필드를 null로 설정하여 관계 끊기
      List<Chat> chats = chatRepository.findByChatRoom(chatRoom);
      for (Chat chat : chats) {
        chat.setChatRoom();
      }
      chatRoomRepository.delete(chatRoom);
    }

    return new ApiResponseDto("채팅방 제거", 200);
  }

  @Override
  public List<ChatRoomListResponseDto> getChatRooms(User user) {
    List<ChatRoomListResponseDto> chatRoomList = chatRoomRepository.findByUsers(user)
        .stream().map(ChatRoomListResponseDto::new).toList();

    return chatRoomList;
  }
}