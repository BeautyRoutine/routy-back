package com.routy.routyback.mapper.user.mypage;

import com.routy.routyback.dto.user.mypage.UserReviewDetailResponse;
import com.routy.routyback.dto.user.mypage.UserReviewResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 나의 리뷰 조회/수정/삭제 관련 Mapper
 */
@Mapper
public interface UserReviewMapper {

    /**
     * 특정 사용자의 리뷰 목록 조회
     *
     * @param userNo 사용자 번호
     * @return 리뷰 목록
     */
    List<UserReviewResponse> selectUserReviews(@Param("userNo") Long userNo);

    /**
     * 특정 리뷰 상세 조회 (본인 리뷰만)
     *
     * @param userNo   사용자 번호
     * @param reviewId 리뷰 번호
     * @return 리뷰 상세 정보
     */
    UserReviewDetailResponse selectUserReviewDetail(
        @Param("userNo") Long userNo,
        @Param("reviewId") Long reviewId
    );

    /**
     * 리뷰 이미지 URL 목록 조회
     *
     * @param reviewId 리뷰 번호
     * @return 이미지 URL 리스트
     */
    List<String> selectReviewImages(@Param("reviewId") Long reviewId);

    /**
     * 리뷰 수정 (별점, 장점, 단점, 본문)
     *
     * @param userNo   사용자 번호
     * @param reviewId 리뷰 번호
     * @param rating   별점
     * @param good     장점
     * @param bad      단점
     * @param content  본문
     * @return 수정된 행 수 (0이면 실패)
     */
    int updateUserReview(
        @Param("userNo") Long userNo,
        @Param("reviewId") Long reviewId,
        @Param("rating") int rating,
        @Param("good") String good,
        @Param("bad") String bad,
        @Param("content") String content
    );

    /**
     * 리뷰 이미지 전체 삭제
     *
     * @param reviewId 리뷰 번호
     * @return 삭제된 행 수
     */
    int deleteReviewImages(@Param("reviewId") Long reviewId);

    /**
     * 리뷰 삭제 (본인 리뷰만 삭제)
     *
     * @param userNo   사용자 번호
     * @param reviewId 리뷰 번호
     * @return 삭제된 행 수
     */
    int deleteUserReview(
        @Param("userNo") Long userNo,
        @Param("reviewId") Long reviewId
    );
}