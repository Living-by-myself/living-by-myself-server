package com.example.livingbymyselfserver.alarm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class NotificationMessage {
  private Long userId;
  private String message;
  private AlarmCategoryEnum alarmCategoryEnum;
}
