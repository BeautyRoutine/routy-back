package com.routy.routyback.dto.user.mypage;

import lombok.Data;

/**
 * 나의 리뷰 수정 요청 DTO
 * - 별점, 장점, 단점, 본문을 수정합니다.
 */
@Data
public class UserReviewUpdateRequest {

    private int rating;      // 수정할 별점
    private String good;     // 수정할 장점
    private String bad;      // 수정할 단점
    private String content;  // 수정할 리뷰 본문
}