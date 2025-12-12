package com.routy.routyback.mapper.user;       // 사용자 날씨 선호지역 관리 Mapper 패키지

import org.apache.ibatis.annotations.Mapper;    // MyBatis Mapper 어노테이션
import org.apache.ibatis.annotations.Param;     // SQL 파라미터 전달용 어노테이션

/**
 * UserWeatherPreferenceMapper
 *
 * - USER_WEATHER_PREFERENCE 테이블을 다루는 MyBatis Mapper 인터페이스
 * - 기능:
 *   1) 사용자 선호 도시 조회
 *   2) 새로 저장 (INSERT)
 *   3) 기존 도시 업데이트
 *   4) 존재 여부 확인
 */
@Mapper
public interface UserWeatherPreferenceMapper {

    /**
     * 특정 사용자의 선호 도시 조회
     *
     * @param userNo USERS 테이블의 PK
     * @return 저장된 도시명 (없으면 null)
     */
    String getPreferredCity(@Param("userNo") Long userNo);

    /**
     * 특정 사용자에 대해 기존 설정이 존재하는지 확인
     *
     * @param userNo 사용자 번호(PK)
     * @return 존재하면 1, 없으면 0
     */
    int existsPreference(@Param("userNo") Long userNo);

    /**
     * 선호 도시 신규 저장
     *
     * @param userNo PK
     * @param cityName 저장할 도시명
     * @return INSERT된 row 수
     */
    int insertPreference(@Param("userNo") Long userNo,
        @Param("cityName") String cityName);

    /**
     * 이미 존재하는 사용자 선호 도시 업데이트
     *
     * @param userNo PK
     * @param cityName 새 도시명
     * @return UPDATE된 row 수
     */
    int updatePreference(@Param("userNo") Long userNo,
        @Param("cityName") String cityName);
}
