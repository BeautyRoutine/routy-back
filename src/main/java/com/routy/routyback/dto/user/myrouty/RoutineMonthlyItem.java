package com.routy.routyback.dto.user.myrouty;

import lombok.Data;

/**
 * RoutineMonthlyItem
 *
 * - 월간 루틴 조회에서 날짜별로 내려줄 데이터 DTO
 * - 한 날짜당 루틴이 하나라는 전제 (하루 1루틴)
 */
@Data
public class RoutineMonthlyItem {

    private Long routineId;    // ROUTINE_ID (루틴 식별자)
    private String date;       // 루틴 날짜 (yyyy-MM-dd 문자열)
    private String summary;    // 루틴 요약 내용
}