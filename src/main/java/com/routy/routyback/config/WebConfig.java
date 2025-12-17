package com.routy.routyback.config;

import java.util.Arrays;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

/**
 * Web MVC 설정
 *
 * CORS 설정은 SecurityConfig에서 처리하므로 여기서는 제거
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${frontend.url}")
    private String allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 URL 허용
            .allowedOrigins(Arrays.stream(allowedOrigins.split(","))
                .map(String::trim)
                .toArray(String[]::new))
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /images/product/** 요청을 static/images/product 폴더에 매핑
        registry.addResourceHandler("/images/product/**")
            .addResourceLocations("classpath:/static/images/product/");
    }

}