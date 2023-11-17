package com.example.livingbymyselfserver.alarm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {

  private final KafkaTemplate<Long, NotificationMessage> kafkaTemplate;

  public void send(NotificationMessage notificationMessage) {
    kafkaTemplate.send("alarm", notificationMessage.getUserId(), notificationMessage);
    log.info("Send to Kafka finished");
  }
}
