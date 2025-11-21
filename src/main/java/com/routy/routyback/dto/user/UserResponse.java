package com.routy.routyback.dto.user;

import com.routy.routyback.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 클라이언트로 내려줄 사용자 정보 DTO
 */
@Getter
@Builder
@AllArgsConstructor
public class UserResponse {

    private Long userNo;
    private String userId;
    private String userName;
    private String userNick;
    private String email;
    private String userAuth;

    public static UserResponse from(User user) {
        if (user == null) {
            return null;
        }
        return UserResponse.builder()
                .userNo(user.getUserNo())
                .userId(user.getUserId())
                .userName(user.getUserName())
                .userNick(user.getUserNick())
                .email(user.getUserEmail())
                .userAuth(user.getUserAuth())
                .build();
    }
}
