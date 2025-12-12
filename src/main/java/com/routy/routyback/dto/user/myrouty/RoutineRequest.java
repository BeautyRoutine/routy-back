package com.routy.routyback.dto.user.myrouty;

import lombok.Data;
import java.util.List;

/**
 * RoutineRequest
 *
 * - 특정 날짜의 루틴을 저장하거나 수정할 때 사용하는 요청 DTO
 * - 루틴의 요약(SUMMARY)과 사용한 제품 목록을 함께 전달한다.
 */
@Data
public class RoutineRequest {

    /** 루틴 요약 메모 (예: "오늘은 보습 위주 루틴") */
    private String summary;

    /** 루틴에서 사용한 제품 목록 */
    private List<UsedProduct> products;

    /** 추가 활동 (예: 스트레칭, 물 2L 마시기 등) */
    private List<String> extraActivities;

    /** 오늘의 총평 (피부 상태 전체 요약) */
    private String dailyReview;

    /**
     * UsedProduct
     *
     * - 루틴에서 사용된 제품 1개에 대한 정보
     * - prdNo: 제품 번호
     * - reaction: GOOD / NORMAL / BAD / NONE
     * - memo: 제품 사용 후 느낌 또는 기록
     */
    @Data
    public static class UsedProduct {

        private Long prdNo;        // 제품 번호
        private String reaction;   // 사용자 반응 (GOOD / NORMAL / BAD / NONE)
        private String memo;       // 제품 관련 메모
        private String alertDate;   // 재사용 알림 날짜 (yyyy-MM-dd)
    }
}
