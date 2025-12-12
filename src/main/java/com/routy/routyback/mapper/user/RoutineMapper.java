package com.routy.routyback.mapper.user;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * RoutineMapper
 *
 * - ROUTINE & ROUTINE_USED_PRODUCT 테이블에 접근하는 MyBatis 매퍼
 * - 월간 조회 / 날짜별 조회 / 저장(INSERT or UPDATE) / 사용 제품 저장 기능을 포함한다.
 */
@Mapper
public interface RoutineMapper {

    /**
     * 특정 사용자의 월간 루틴 캘린더 조회
     * - 날짜별로 요약 정보만 가져온다.
     *
     * @param userNo  사용자 PK
     * @param year    조회 연도
     * @param month   조회 월
     * @return 일자별 루틴 기록 목록
     */
    List<Map<String, Object>> selectMonthlyRoutine(
        @Param("userNo") Long userNo,
        @Param("year") int year,
        @Param("month") int month
    );

    /**
     * 특정 날짜 루틴 상세 조회
     *
     * @param userNo 사용자 PK
     * @param date   yyyy-MM-dd 형식 문자열
     * @return 상세 루틴 데이터
     */
    Map<String, Object> selectRoutineByDate(
        @Param("userNo") Long userNo,
        @Param("date") String date
    );

    /**
     * 특정 날짜에 루틴이 존재하는지 확인
     *
     * @param userNo 사용자 PK
     * @param date   yyyy-MM-dd
     * @return 존재하면 1, 없으면 0
     */
    int existsRoutine(
        @Param("userNo") Long userNo,
        @Param("date") String date
    );

    /**
     * 새로운 루틴 INSERT
     *
     * @param userNo  사용자 PK
     * @param date    루틴 날짜
     * @param summary 요약 메모
     */
    void insertRoutine(
        @Param("userNo") Long userNo,
        @Param("date") String date,
        @Param("summary") String summary,
        @Param("extraActivities") String extraActivities,
        @Param("dailyReview") String dailyReview
    );

    /**
     * 기존 루틴 UPDATE
     *
     * @param userNo  사용자 PK
     * @param date    루틴 날짜
     * @param summary 요약 메모
     */
    void updateRoutine(
        @Param("userNo") Long userNo,
        @Param("date") String date,
        @Param("summary") String summary,
        @Param("extraActivities") String extraActivities,
        @Param("dailyReview") String dailyReview
    );

    /**
     * 특정 날짜 루틴의 사용 제품 전체 삭제
     */
    void deleteRoutineUsedProducts(
        @Param("userNo") Long userNo,
        @Param("date") String date
    );

    /**
     * 특정 날짜 루틴 삭제 (ROUTINE 테이블)
     *
     * @param userNo 사용자 PK
     * @param date   yyyy-MM-dd
     */
    void deleteRoutine(
        @Param("userNo") Long userNo,
        @Param("date") String date
    );

    /**
     * 특정 날짜 루틴에 제품 1개 추가
     *
     * @param routineId ROUTINE PK
     * @param prdNo     제품 번호
     * @param reaction  사용자 반응 (GOOD/NORMAL/BAD/NONE)
     * @param memo      메모
     */
    void insertRoutineUsedProduct(
        @Param("routineId") Long routineId,
        @Param("prdNo") Long prdNo,
        @Param("reaction") String reaction,
        @Param("memo") String memo,
        @Param("alertDate") String alertDate
    );

    /**
     * ROUTINE_ID 조회 (날짜 + 사용자 기준)
     */
    Long findRoutineId(
        @Param("userNo") Long userNo,
        @Param("date") String date
    );

    /**
     * 특정 ROUTINE_ID 기준 사용 제품 상세 조회
     * - ROUTINE_USED_PRODUCT + PRODUCT 조인 결과를 반환한다.
     */
    List<Map<String, Object>> selectRoutineUsedProducts(
        @Param("routineId") Long routineId
    );
}
