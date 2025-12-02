package com.routy.routyback.service.user.mypage;

import com.routy.routyback.dto.user.mypage.UserReviewDetailResponse;
import com.routy.routyback.dto.user.mypage.UserReviewResponse;
import com.routy.routyback.dto.user.mypage.UserReviewUpdateRequest;

import java.util.List;

/**
 * 나의 리뷰 서비스 인터페이스
 * - 마이페이지에서 사용하는 리뷰 목록/상세/수정/삭제 기능 정의
 */
public interface IUserReviewService {

    /**
     * 특정 사용자의 리뷰 목록을 조회합니다.
     *
     * @param userNo 사용자 번호
     * @return 사용자가 작성한 리뷰 목록
     */
    List<UserReviewResponse> getUserReviews(Long userNo);

    /**
     * 특정 리뷰의 상세 정보를 조회합니다.
     * 본인 리뷰인지 체크합니다.
     *
     * @param userNo   사용자 번호
     * @param reviewId 리뷰 번호
     * @return 리뷰 상세 정보 (없으면 null 반환)
     */
    UserReviewDetailResponse getReviewDetail(Long userNo, Long reviewId);

    /**
     * 리뷰 내용을 수정합니다.
     * 별점, 장점, 단점, 본문(content)을 수정합니다.
     *
     * @param userNo   사용자 번호
     * @param reviewId 리뷰 번호
     * @param request  수정할 리뷰 내용
     * @return 수정 성공 여부 (false면 없는 리뷰)
     */
    boolean updateReview(Long userNo, Long reviewId, UserReviewUpdateRequest request);

    /**
     * 리뷰를 삭제합니다.
     * 리뷰 이미지 삭제 후 리뷰를 삭제합니다.
     *
     * @param userNo   사용자 번호
     * @param reviewId 리뷰 번호
     * @return 삭제 성공 여부 (false면 없는 리뷰)
     */
    boolean deleteReview(Long userNo, Long reviewId);
}