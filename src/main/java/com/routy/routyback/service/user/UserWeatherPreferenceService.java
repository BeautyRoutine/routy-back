package com.routy.routyback.service.user;   // 사용자 선호 날씨 지역 관리 서비스 패키지

import com.routy.routyback.mapper.user.UserMapper;                     // userId → userNo 변환용 매퍼
import com.routy.routyback.mapper.user.UserWeatherPreferenceMapper;    // 선호 지역 처리 매퍼
import lombok.RequiredArgsConstructor;                                 // 생성자 자동 주입
import org.springframework.stereotype.Service;                         // 스프링 서비스 빈 등록

/**
 * UserWeatherPreferenceService
 *
 * - 사용자 선호 날씨 지역을 저장/조회하는 서비스
 * - Controller에서는 userId(String)을 사용하고,
 *   여기에서 userId → userNo(Long)으로 변환한 뒤 DB 접근을 수행한다.
 */
@Service
@RequiredArgsConstructor
public class UserWeatherPreferenceService {

    private final UserMapper userMapper;                               // userId → userNo 변환
    private final UserWeatherPreferenceMapper preferenceMapper;        // 선호 지역 DB 접근 매퍼

    /**
     * 사용자의 선호 도시 조회
     * - 설정이 없으면 null 반환 → Controller에서 기본값 처리
     *
     * @param userId 문자열 사용자 아이디
     * @return 도시명 또는 null
     */
    public String getPreferredCity(String userId) {
        Long userNo = userMapper.findUserNoByUserId(userId);           // userId → userNo 변환
        return preferenceMapper.getPreferredCity(userNo);               // DB에 저장된 도시 반환
    }

    /**
     * 선호도 조회 + 기본값으로 반환
     * - 선호 도시가 없으면 WeatherController 기본값을 사용하도록 null 반환
     *
     * @param userId 문자열 사용자 아이디
     * @return 존재하면 도시명 / 없으면 null
     */
    public String getPreferredCityOrDefault(String userId) {
        return getPreferredCity(userId);                                // 기본값 처리는 Controller에서 수행
    }

    /**
     * 사용자의 선호 도시 저장 (없으면 INSERT, 있으면 UPDATE)
     *
     * @param userId   문자열 사용자 아이디
     * @param cityName 저장할 도시명
     */
    public void updatePreferredCity(String userId, String cityName) {
        Long userNo = userMapper.findUserNoByUserId(userId);            // userId → userNo 변환

        // 기존 설정 존재 여부 확인
        boolean exists = preferenceMapper.existsPreference(userNo) > 0;

        if (exists) {
            preferenceMapper.updatePreference(userNo, cityName);        // UPDATE
        } else {
            preferenceMapper.insertPreference(userNo, cityName);        // INSERT
        }
    }
}
