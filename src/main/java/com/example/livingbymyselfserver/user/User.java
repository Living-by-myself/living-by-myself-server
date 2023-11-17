package com.example.livingbymyselfserver.user;

import com.example.livingbymyselfserver.chat.entity.Chat;
import com.example.livingbymyselfserver.chat.entity.ChatRoom;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor
@DynamicUpdate
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long id;

  @Column(nullable = false, unique = true)
  private String username;

  private String password;

  private String nickname;

  private String address;

  @Column(unique = true)
  private String phoneNumber;

  @Enumerated(value = EnumType.STRING)
  private UserRoleEnum role;

  @Column(name = "oauth_provider")
  @Enumerated(value = EnumType.STRING)
  private OAuthProviderEnum oAuthProvider;

  private Long cash;

  private Long level;

  @ManyToOne
  @JoinColumn(name = "chat_room_id")
  private ChatRoom chatRoom;

  @ManyToOne
  @JoinColumn(name = "chat_id")
  private Chat chat;

  public User(String username, String password, String nickname, String phoneNumber, UserRoleEnum role,
      OAuthProviderEnum oAuthProvider) {
    this.username = username;
    this.password = password;
    this.nickname = nickname;
    this.phoneNumber = phoneNumber;
    this.role = role;
    this.oAuthProvider = oAuthProvider;
  }

  public User(String username, String password, String nickname, String address, String phoneNumber,
      UserRoleEnum role, OAuthProviderEnum oAuthProvider, Long cash, Long level) {
    this.username = username;
    this.password = password;
    this.nickname = nickname;
    this.address = address;
    this.phoneNumber = phoneNumber;
    this.role = role;
    this.oAuthProvider = oAuthProvider;
    this.cash = cash;
    this.level = level;
  }

  public User(String username, String nickname, UserRoleEnum role, OAuthProviderEnum oAuthProvider) {
    this.username = username;
    this.password = null;
    this.nickname = nickname;
    this.role = role;
    this.oAuthProvider = oAuthProvider;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public void setRole(UserRoleEnum role) {
    this.role = role;
  }

  public void setCash(Long cash) {
    this.cash = cash;
  }

  public void setOAuthProvider(OAuthProviderEnum oAuthProvider) {
    this.oAuthProvider = oAuthProvider;
  }
}
