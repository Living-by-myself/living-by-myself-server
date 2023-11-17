package com.example.livingbymyselfserver.alarm;

import com.example.livingbymyselfserver.user.User;
import com.example.livingbymyselfserver.user.UserService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

  private final EmitterService emitterService;
  private final EmitterRepository emitterRepository;
  private final UserService userService;

  @KafkaListener(topics = "alarm")
  public void listen(NotificationMessage message) {
    String userId = message.getUserId().toString();
    log.info("Consume the event {}", message);
    User user = userService.findUser(message.getUserId());
    Alarm notifications = Alarm.builder()
        .user(user)
        .message(message.getMessage())
        .alarmCategoryEnum(message.getAlarmCategoryEnum()).build();


    Map<String, SseEmitter> sseEmitters = emitterRepository.findAllEmitterStartWithById(userId);
    sseEmitters.forEach(
        (key, emitter) -> {
          emitterRepository.saveEventCache(key, notifications);
          emitterService.sendToClient(emitter, key, notifications);
        }
    );
  }
}
