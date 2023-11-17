package com.example.livingbymyselfserver.chat;

import com.example.livingbymyselfserver.attachment.user.AttachmentUserUrlRepository;
import com.example.livingbymyselfserver.chat.entity.Chat;
import com.example.livingbymyselfserver.chat.entity.ChatRoom;
import com.example.livingbymyselfserver.chat.entity.dto.ChatMessageResponseDto;
import com.example.livingbymyselfserver.chat.entity.dto.ChatRoomListResponseDto;
import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.user.User;
import com.example.livingbymyselfserver.user.UserRepository;
import com.example.livingbymyselfserver.user.UserService;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

  private final ChatRoomRespository chatRoomRepository;
  private final ChatRepository chatRepository;
  private final AttachmentUserUrlRepository attachmentUserUrlRepository;
  private final UserService userService;
  private final UserRepository userRepository;

  @Override
  public Chat createChat(Long roomNo, Long userId, String msg) {
    ChatRoom chatRoom = getRoom(roomNo);
    User user = userService.findUser(userId);
    Chat chat = new Chat(msg,chatRoom, user,attachmentUserUrlRepository);
    chatRepository.save(chat);

    return chat;
  }

  @Override
  public ApiResponseDto createChatRoom(Long userId,List<Long> userIdList) {
    User user = userService.findUser(userId);

    for(int i =0;i<userIdList.size();i++){
      if(userId.equals(userIdList.get(i))){
        throw new IllegalArgumentException("자기 자신이랑 채팅은 안돼요ㅠ");
      }
    }

//    if (findChatRoom != null) {
//      throw new IllegalArgumentException("생성되 있는 채팅방입니다");
//    }
    List<User> users = userRepository.findByIdIn(userIdList);

    ChatRoom chatRoom = new ChatRoom(user, users);
    chatRoomRepository.save(chatRoom);

    return new ApiResponseDto("채팅 방 생성 완료", 201);
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