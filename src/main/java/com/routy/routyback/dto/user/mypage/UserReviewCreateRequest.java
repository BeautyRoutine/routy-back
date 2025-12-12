package com.routy.routyback.dto.user.mypage;

import lombok.Data;
import java.util.List;

/**
 * 리뷰 등록 요청 DTO
 * - 사용자가 리뷰 작성 시 전달하는 데이터
 */
@Data
public class UserReviewCreateRequest {

    /** 상품 번호 */
    private Long productId;

    /** 리뷰 별점 (1~5) */
    private int rating;

    /** 리뷰 상세 내용 */
    private String content;

    /** 장점 */
    private String good;

    /** 단점 */
    private String bad;

    /** 리뷰 이미지 URL 목록 */
    private List<String> images;
}