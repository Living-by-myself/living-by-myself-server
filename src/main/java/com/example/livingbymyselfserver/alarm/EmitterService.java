package com.example.livingbymyselfserver.alarm;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmitterService {
  private final EmitterRepository emitterRepository;
  private final AlarmRepository alarmRepository;

  public static final Long DEFAULT_TIMEOUT = 3600L * 1000;
  private static final int MAX_NOTIFICATIONS_COUNT = 6;

  public void sendToClient(SseEmitter emitter, String emitterId, Object data) {
    try {
      emitter.send(SseEmitter.event()
          .id(emitterId)
          .data(data));

      Alarm alarm = (Alarm) data;
      // alarm save
      alarmRepository.save(alarm);

      log.info("Kafka로 부터 전달 받은 메세지 전송. emitterId : {}, message : {}", emitterId, data);
    } catch (IOException e) {
      emitterRepository.deleteById(emitterId);
      log.error("메시지 전송 에러 : {}", e);
    }
  }

  public SseEmitter addEmitter(Long userId) {
    SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
    emitterRepository.save(userId.toString(), emitter);
    emitter.onCompletion(() -> {
      log.info("onCompletion callback");
      emitterRepository.deleteById(userId.toString());
    });
    emitter.onTimeout(() -> {
      log.info("onTimeout callback");
      emitterRepository.deleteById(userId.toString());
    });

    try {
      log.info("send");
      emitter.send(SseEmitter.event()
          .id("id")
          .name("alarm")
          .data("connect completed"));
    } catch (IOException exception) {
      throw new IllegalArgumentException(exception);
//          SimpleSnsApplicationException(ErrorCode.NOTIFICATION_CONNECT_ERROR);
    }
    return emitter;
  }
}