package com.example.livingbymyselfserver.user.dto;

import lombok.Getter;

@Getter
public class SignupRequestDto {
  private String username;
  private String password;
  private String passwordCheck;
  private String phoneNumber;
  private String adress;
}
