package com.example.livingbymyselfserver.chat.entity;

import com.example.livingbymyselfserver.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "chat_rooms")
@Getter
@Setter
@NoArgsConstructor
public class ChatRoom {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "chat_room_id")
  private Long id;

  String title;

  String lastChatTime;

  String lastChatMsg;

  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinTable(
      name = "chat_room_user",
      joinColumns = @JoinColumn(name = "chat_room_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id")
  )
  private Set<User> users = new HashSet<>();

  public ChatRoom(User user, List<User> users, String title) {
    this.users = new HashSet<>(users);
    addUser(user);
    this.title = title;
  }
  public void setLastChatMsg(String lastChatMsg){
    this.lastChatMsg =  lastChatMsg;
  }
  public void setLastChatTime(String lastChatTime){
    this.lastChatTime = lastChatTime;
  }
  public void addUser(User user){
    this.users.add(user);
  }
}
