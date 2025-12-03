package com.routy.routyback.dto.user.mypage;

import lombok.Data;

/**
 * 클레임 신청 요청 DTO
 * - 사용자가 취소/반품/교환 신청 시 전달하는 데이터
 */
@Data
public class UserClaimRequest {

    /** 주문 번호 */
    private Long orderId;

    /** 클레임 유형 (CANCEL / REFUND / EXCHANGE) */
    private String type;

    /** 신청 사유 */
    private String reason;
}