package com.routy.routyback.dto.user.myrouty;

import com.routy.routyback.dto.weather.WeatherResponse;
import lombok.Data;

/**
 * DashboardResponse
 *
 * - MyRouty 대시보드에서 필요한 정보를 한 번에 내려주는 DTO
 * - 날씨 + 루틴 요약 + 제품 요약 + 계절 팁 등 확장 가능
 */
@Data
public class DashboardResponse {

    /** 현재 날씨 정보 */
    private WeatherResponse weather;

    /** 오늘 루틴 요약 (summary) */
    private String todayRoutineSummary;

    /** 오늘 루틴이 존재하는지 여부 */
    private boolean routineExists;

    /** 현재 사용 중인 제품 개수 */
    private int usedProductCount;

    /** 계절 기반 스킨케어 팁 문자열 */
    private String skincareTip;

    /** 계절 변화 관련 알림 문자열 */
    private String seasonalAlert;
}