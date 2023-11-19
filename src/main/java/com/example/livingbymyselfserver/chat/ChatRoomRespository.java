package com.example.livingbymyselfserver.chat;

import com.example.livingbymyselfserver.chat.entity.ChatRoom;
import com.example.livingbymyselfserver.user.User;
import java.util.Arrays;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRespository extends JpaRepository<ChatRoom,Long> {
  List<ChatRoom> findByUsers(User user);
}
