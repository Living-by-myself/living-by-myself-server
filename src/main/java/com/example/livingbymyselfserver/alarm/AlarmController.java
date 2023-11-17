package com.example.livingbymyselfserver.alarm;

import com.example.livingbymyselfserver.security.UserDetailsImpl;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AlarmController {

  private final EmitterService emitterService;
  private final KafkaProducer notificationsService;

  @GetMapping(value = "/api/sse-connection", produces = "text/event-stream")
  public SseEmitter stream(@AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
    return emitterService.addEmitter(userDetails.getUser().getId());
  }
}
