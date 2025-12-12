package com.routy.routyback.controller.weather;

import com.routy.routyback.common.ApiResponse;
import com.routy.routyback.dto.weather.WeatherResponse;
import com.routy.routyback.service.weather.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    /**
     * 도시 이름 기준 오늘의 날씨 조회 API
     *
     * @param city FE에서 보낸 도시명 (예: Seoul, Busan 등)
     * @return CommonResponse<WeatherResponse>
     */
    @GetMapping("/today")
    public ApiResponse<WeatherResponse> getTodayWeather(@RequestParam(required = false) String city) {

        // 기본값 처리 (null 또는 빈 문자열)
        if (city == null || city.trim().isEmpty()) {
            city = "Seoul"; // 기본값을 서울로 설정 (원하면 FE에서 반드시 보내도록 강제 가능)
        }

        WeatherResponse weather = weatherService.getTodayWeatherByCity(city.trim());

        return ApiResponse.success(weather);
    }
}