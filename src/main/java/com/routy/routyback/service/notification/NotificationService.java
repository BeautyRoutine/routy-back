package com.routy.routyback.service.notification;

import com.routy.routyback.dto.notification.NotificationResponse;
import com.routy.routyback.mapper.notification.NotificationMapper;
import com.routy.routyback.mapper.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 알림 비즈니스 로직을 담당하는 서비스
 */
@Service
@RequiredArgsConstructor
public class NotificationService {

    // userId -> userNo 조회용 (이미 있을 가능성 높음)
    private final UserMapper userMapper;

    // 알림 쿼리용 Mapper
    private final NotificationMapper notificationMapper;

    /**
     * userId 로부터 userNo 를 조회하는 내부 메서드
     *
     * @param userId USERS.USERID
     * @return USERS.USERNO
     */
    private Long getUserNo(String userId) {
        // 예시: UserMapper에 아래와 같은 메서드가 필요함
        // Long findUserNoByUserId(String userId);
        return userMapper.findUserNoByUserId(userId);
    }

    /**
     * 읽지 않은 알림 개수 조회
     */
    public int getUnreadCount(String userId) {
        Long userNo = getUserNo(userId);
        return notificationMapper.getUnreadCount(userNo);
    }

    /**
     * 알림 목록 조회
     */
    public List<NotificationResponse> getNotifications(String userId) {
        Long userNo = getUserNo(userId);
        return notificationMapper.getNotificationList(userNo);
    }

    /**
     * 알림 전체 읽음 처리
     */
    public void readAll(String userId) {
        Long userNo = getUserNo(userId);
        notificationMapper.readAll(userNo);
    }

    /**
     * 특정 알림 하나만 읽음 처리
     */
    public void readOne(String userId, Long notiId) {
        // notiId만으로 읽음 처리 (userId는 조회용으로만 사용)
        notificationMapper.readOne(notiId);
    }

    /**
     * 특정 알림 하나 삭제
     */
    public void deleteOne(String userId, Long notiId) {
        notificationMapper.deleteOne(notiId);
    }

    /**
     * 전체 알림 삭제
     */
    public void deleteAll(String userId) {
        Long userNo = getUserNo(userId);
        notificationMapper.deleteAll(userNo);
    }

}