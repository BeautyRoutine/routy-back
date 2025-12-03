package com.routy.routyback.dto.user.mypage;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 클레임 조회 응답 DTO
 * - 사용자가 조회할 때 보여주는 데이터
 */
@Data
public class UserClaimResponse {

    /** 클레임 ID (PK) */
    private Long claimId;

    /** 주문 번호 */
    private Long orderId;

    /** 처리 유형 */
    private String type;

    /** 현재 상태 (REQUEST / COMPLETED 등) */
    private String status;

    /** 신청 사유 */
    private String reason;

    /** 신청 일시 */
    private LocalDateTime createdAt;
}