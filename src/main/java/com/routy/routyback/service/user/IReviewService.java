package com.routy.routyback.service.user;

import com.routy.routyback.dto.review.ReviewCreateRequest;
import com.routy.routyback.dto.review.ReviewLikeResponse;
import com.routy.routyback.dto.review.ReviewListResponse;
import com.routy.routyback.dto.review.ReviewResponse;
import com.routy.routyback.dto.review.ReviewUpdateRequest;

public interface IReviewService {

    ReviewListResponse getReviewList(int prdNo, int page, int limit, String sort); // 리뷰 목록 조회

    ReviewResponse createReview(int prdNo, ReviewCreateRequest request); // 리뷰 저장

    ReviewResponse updateReview(int revNo, ReviewUpdateRequest request); // 리뷰 수정

    void deleteReview(int revNo); // 리뷰삭제

    ReviewLikeResponse toggleLike(int revNo, int userNo); // 리뷰 좋아요

}
