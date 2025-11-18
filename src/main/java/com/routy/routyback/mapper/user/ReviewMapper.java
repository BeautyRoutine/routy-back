package com.routy.routyback.mapper.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.routy.routyback.domain.ReviewVO;

@Mapper
public interface ReviewMapper {

    List<ReviewVO> findReviews(Map<String, Object> params); // 리뷰 목록 조회

    // db 결과로 만든 Review 객체를 list 자료구조로 담고, 다양한 타입이니 Object
    int countReviews(int prdNo);  // 리뷰 개수 조회

    Double findAverageRating(int prdNo); // 상품 평균 별점 조회

    int insertReview(ReviewVO review);  // 리뷰 추가, ReviewVO 사용

    ReviewVO findReview(int revNo);   // 리뷰 찾기

    int updateReview(ReviewVO review);  // 리뷰 수정

    int deleteReview(int revNo);

    Integer findLikeByUserAndReview(@Param("revNo") int revNo, @Param("userNo") int userNo); // 리뷰 좋아요 체크, @Param은 이름표

    void insertLike(@Param("revNo") int revNo, @Param("userNo") int userNo); // 좋아요 추가

    void deleteLike(@Param("revNo") int revNo, @Param("userNo") int userNo); // 좋아요 삭제

    int countLikes(int revNo); // 리뷰 좋아요 카운트

    void updateReviewTrustScore(ReviewVO review); // 계산된 리뷰 신뢰도 점수 및 등급 업데이트
}
