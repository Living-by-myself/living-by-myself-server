package com.example.livingbymyselfserver.alarm;

import com.example.livingbymyselfserver.user.User;
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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Table(name = "alarms")
@NoArgsConstructor
@DynamicUpdate
public class Alarm {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String message;

  @Enumerated(value = EnumType.STRING)
  private AlarmCategoryEnum alarmCategoryEnum;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Builder
  public Alarm(String message, AlarmCategoryEnum alarmCategoryEnum, User user) {
    this.message = message;
    this.alarmCategoryEnum = alarmCategoryEnum;
    this.user = user;
  }
}
