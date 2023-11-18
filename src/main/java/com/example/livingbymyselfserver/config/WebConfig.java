package com.example.livingbymyselfserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")    // 프론트 경로
                .allowedMethods("*")   // 허용할 Methods 경로
                .allowCredentials(true); // 인증 정보 허용
    }
}
