package com.routy.routyback.mapper.notification;

import com.routy.routyback.dto.notification.NotificationResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 알림 관련 MyBatis Mapper
 */
@Mapper
public interface NotificationMapper {

    /**
     * 특정 사용자(userNo)의 읽지 않은 알림 개수 조회
     *
     * @param userNo USERS.USERNO
     * @return 읽지 않은 알림 수
     */
    int getUnreadCount(Long userNo);

    /**
     * 특정 사용자(userNo)의 알림 목록 조회
     *
     * @param userNo USERS.USERNO
     * @return 알림 목록 (최신순)
     */
    List<NotificationResponse> getNotificationList(Long userNo);

    /**
     * 특정 사용자(userNo)의 모든 알림을 읽음 처리
     *
     * @param userNo USERS.USERNO
     * @return 업데이트된 행 수
     */
    int readAll(Long userNo);

    /**
     * 특정 알림(notiId) 하나만 읽음 처리
     *
     * @param notiId USER_NOTIFICATION.NOTI_ID
     * @return 업데이트된 행 수
     */
    int readOne(Long notiId);

    /**
     * 특정 알림(notiId) 삭제
     *
     * @param notiId USER_NOTIFICATION.NOTI_ID
     * @return 삭제된 행 수
     */
    int deleteOne(Long notiId);

    /**
     * 특정 사용자(userNo)의 모든 알림 삭제
     *
     * @param userNo USERS.USERNO
     * @return 삭제된 행 수
     */
    int deleteAll(Long userNo);


}