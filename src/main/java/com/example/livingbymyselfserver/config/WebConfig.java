package com.example.livingbymyselfserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("https://localhost:5173") // 또는 "https://*.example.com"과 같은 구체적인 패턴
                .allowedHeaders("*")   // header 모든 요청 허용
                .allowedMethods("*")   // 허용할 Methods 경로
                .allowCredentials(true) // 인증 정보 허용
                .maxAge(3600);
    }
}
