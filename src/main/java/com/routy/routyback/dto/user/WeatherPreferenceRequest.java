package com.routy.routyback.dto.user;   // MyRouty 전용 DTO 패키지

import lombok.Data;                               // Lombok @Data : getter/setter/toString 자동 생성

/**
 * WeatherPreferenceRequest
 *
 * - 사용자의 날씨 선호 지역을 변경할 때 사용하는 요청 DTO
 * - Controller 에서 요청 바디(JSON)로 cityName 하나만 받는다.
 *   예) { "cityName": "Seoul" }
 */
@Data                                             // 모든 필드에 대한 getter/setter 등을 Lombok이 자동 생성
public class WeatherPreferenceRequest {           // 날씨 선호 도시 변경 요청을 표현하는 DTO 클래스

    private String cityName;                     // 사용자가 저장하고자 하는 도시 이름 (예: "Seoul", "Busan")
}
