package com.routy.routyback.service.weather;

import com.routy.routyback.dto.weather.Season;
import com.routy.routyback.dto.weather.WeatherResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * OpenWeather API 를 호출해서
 * 온도 / 습도 / 풍속 / 자외선 + 계절 정보를 만드는 서비스
 */
@Service
public class WeatherService {

    // application.properties 에서 불러오는 API Key
    @Value("${weather.api.key}")
    private String apiKey;

    // 현재 날씨 조회용 기본 URL (2.5/weather)
    @Value("${weather.api.url}")
    private String weatherApiUrl;

    // One Call (UV 지수용) 기본 URL – properties 에 추가해두면 좋음
    // weather.onecall.url=https://api.openweathermap.org/data/2.5/onecall
    @Value("${weather.onecall.url:https://api.openweathermap.org/data/2.5/onecall}")
    private String oneCallApiUrl;

    // OpenWeather 호출용 RestTemplate (타임아웃 설정 포함)
    private static final RestTemplate restTemplate;

    static {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);
        restTemplate = new RestTemplate(factory);
    }

    /**
     * 도시 이름 기준으로 오늘의 날씨 정보를 조회
     *
     * @param city 도시 이름 (예: "Seoul", "Busan")
     * @return WeatherResponse (온도, 습도, 풍속, UV, 계절 등)
     */
    public WeatherResponse getTodayWeatherByCity(String city) {

        // 도시 이름을 URL 에 안전하게 넣기 위해 인코딩
        String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);

        // 1. 현재 날씨 조회 URL 생성
        String currentWeatherUrl = UriComponentsBuilder.fromHttpUrl(weatherApiUrl)
            .queryParam("q", encodedCity)          // 도시명 기준 검색
            .queryParam("appid", apiKey)          // API Key
            .queryParam("units", "metric")        // 섭씨 사용
            .build()
            .toString();

        // 2. 현재 날씨 API 호출 (응답을 Map 형태로 받기)
        Map<String, Object> currentRaw = restTemplate.getForObject(currentWeatherUrl, Map.class);

        // 2-1. null 방어 코드 (실패 시 예외 던지거나 기본값 처리 가능)
        if (currentRaw == null) {
            throw new IllegalStateException("OpenWeather current weather response is null");
        }

        // 3. 기본 응답에서 필요한 값들 파싱
        // 도시 이름
        String cityName = currentRaw.get("name") != null ? currentRaw.get("name").toString() : "";

        // main 객체 안의 온도 / 습도
        Map<String, Object> main =
            currentRaw.get("main") instanceof Map ? (Map<String, Object>) currentRaw.get("main") : null;
        double temp = extractNumber(main, "temp");
        int humidity = (int) extractNumber(main, "humidity");

        // wind 객체 안의 풍속
        Map<String, Object> wind =
            currentRaw.get("wind") instanceof Map ? (Map<String, Object>) currentRaw.get("wind") : null;
        double windSpeed = extractNumber(wind, "speed");

        // coord 객체 안의 위도/경도 – UV 조회에 필요
        Map<String, Object> coord =
            currentRaw.get("coord") instanceof Map ? (Map<String, Object>) currentRaw.get("coord") : null;
        double lat = extractNumber(coord, "lat");
        double lon = extractNumber(coord, "lon");

        // 4. 위도/경도로 One Call API 를 호출해서 UV Index 가져오기
        String oneCallUrl = UriComponentsBuilder.fromHttpUrl(oneCallApiUrl)
            .queryParam("lat", lat)              // 위도
            .queryParam("lon", lon)              // 경도
            .queryParam("appid", apiKey)         // API Key
            .queryParam("exclude", "minutely,hourly,daily,alerts") // 필요한 건 current 만
            .build()
            .toString();

        Map<String, Object> oneCallRaw = restTemplate.getForObject(oneCallUrl, Map.class);

        double uvIndex = 0.0;
        if (oneCallRaw != null) {
            // current 객체 안의 uvi 값
            Map<String, Object> current =
                oneCallRaw.get("current") instanceof Map ? (Map<String, Object>) oneCallRaw.get("current") : null;
            if (current != null && current.get("uvi") != null) {
                uvIndex = extractNumber(current, "uvi");
            }
        }

        // 5. 계절 및 곧 계절이 바뀔지 여부 계산 (한국 기준 간단 로직)
        Season season = resolveSeason();
        boolean seasonChangeSoon = isSeasonChangeSoon();

        // 6. DTO 에 값 세팅
        WeatherResponse response = new WeatherResponse();
        response.setCity(cityName);
        response.setTemperature(temp);
        response.setHumidity(humidity);
        response.setWindSpeed(windSpeed);
        response.setUvIndex(uvIndex);
        response.setSeason(season);
        response.setSeasonChangeSoon(seasonChangeSoon);

        return response;
    }

    /**
     * 현재 날짜 기준으로 계절을 계산
     * 아주 단순하게 월 기준으로만 나눔 (한국 기준)
     */
    private Season resolveSeason() {
        // 서울 타임존 기준 현재 날짜
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        int month = today.getMonthValue();

        // 3~5월: 봄, 6~8월: 여름, 9~11월: 가을, 나머지: 겨울
        if (month >= 3 && month <= 5) {
            return Season.SPRING;
        } else if (month >= 6 && month <= 8) {
            return Season.SUMMER;
        } else if (month >= 9 && month <= 11) {
            return Season.AUTUMN;
        } else {
            return Season.WINTER;
        }
    }

    /**
     * 곧 계절이 바뀔 것 같은지 여부를 간단하게 계산
     * 2, 5, 8, 11월 → 다음 달에 계절이 바뀐다고 간주
     */
    private boolean isSeasonChangeSoon() {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        int month = today.getMonthValue();

        // 2월(→봄), 5월(→여름), 8월(→가을), 11월(→겨울) 근처라면 true
        return month == 2 || month == 5 || month == 8 || month == 11;
    }

    /**
     * Map 에서 지정된 키의 값을 Number 타입으로 안전하게 추출하는 헬퍼 메서드
     * 값이 없거나 Number 타입이 아니면 0.0 반환
     */
    private double extractNumber(Map<String, Object> map, String key) {
        if (map == null || !map.containsKey(key) || map.get(key) == null) {
            return 0.0;
        }
        Object value = map.get(key);
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        try {
            return Double.parseDouble(value.toString());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}