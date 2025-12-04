package com.routy.routyback.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

/**
 * Web MVC 설정
 *
 * CORS 설정은 SecurityConfig에서 처리하므로 여기서는 제거
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /images/product/** 요청을 static/images/product 폴더에 매핑
        registry.addResourceHandler("/images/product/**")
            .addResourceLocations("classpath:/static/images/product/");
    }

    // CORS 설정 제거 (SecurityConfig에서 처리)
}