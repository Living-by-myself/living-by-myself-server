package com.example.livingbymyselfserver.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenResponseDto {
  private final String atk;
  private final String rtk;
}
