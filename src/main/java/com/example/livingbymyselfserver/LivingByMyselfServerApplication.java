package com.example.livingbymyselfserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LivingByMyselfServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(LivingByMyselfServerApplication.class, args);
  }

}
