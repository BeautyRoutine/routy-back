package com.routy.routyback.dto.weather;

import lombok.Data;

/**
 * FE로 내려줄 날씨 응답 DTO
 */
@Data
public class WeatherResponse {

    // 조회 기준 도시 이름
    private String city;

    // 현재 기온 (섭씨)
    private double temperature;

    // 현재 습도 (%)
    private int humidity;

    // 현재 풍속 (m/s)
    private double windSpeed;

    // 현재 자외선 지수 (UV Index)
    private double uvIndex;

    // 현재 계절
    private Season season;

    // 곧 계절이 바뀔 것 같은지 여부 (간단한 월 기준 로직)
    private boolean seasonChangeSoon;
}