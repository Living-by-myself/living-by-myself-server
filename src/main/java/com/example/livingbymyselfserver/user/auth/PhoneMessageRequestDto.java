package com.example.livingbymyselfserver.user.auth;

import lombok.Getter;

@Getter
public class PhoneMessageRequestDto {
  private String phoneNumber;
  private int code;
}
