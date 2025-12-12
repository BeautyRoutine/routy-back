package com.routy.routyback.dto.notification;

import lombok.Data;

/**
 * 헤더 알림 목록에서 사용할 응답 DTO
 */
@Data
public class NotificationResponse {

    // 알림 PK (USER_NOTIFICATION.NOTI_ID)
    private Long notiId;

    // 알림 제목 (예: 배송 중입니다)
    private String title;

    // 알림 내용 (예: 고객님의 상품이 현재 배송 중입니다.)
    private String message;

    // 알림 유형 (delivery, like, comment, promotion 등)
    private String type;

    // 읽지 않음 여부 플래그 (DB의 IS_READ 그대로 사용: 'Y' / 'N')
    private String unread;

    // 생성 시각(문자열) – FE에서 그대로 표시용으로 사용
    private String createdAt;
}