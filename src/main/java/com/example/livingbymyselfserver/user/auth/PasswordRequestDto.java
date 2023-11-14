package com.example.livingbymyselfserver.user.auth;

import lombok.Getter;

@Getter
public class PasswordRequestDto {
  private String newPassword;
  private String newPasswordCheck;
}
