package com.routy.routyback.dto.user.myrouty;

import lombok.Data;
import java.util.List;

@Data
public class RoutineMonthlyCalendarResponse {

    private int year;                        // 조회 연도
    private int month;                       // 조회 월
    private List<RoutineMonthlyItem> routines; // 루틴이 존재하는 날짜 리스트
}
