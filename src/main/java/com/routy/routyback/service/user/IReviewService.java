package com.routy.routyback.service.user;

import com.routy.routyback.dto.review.ReviewCreateRequest;
import com.routy.routyback.dto.review.ReviewLikeResponse;
import com.routy.routyback.dto.review.ReviewListResponse;
import com.routy.routyback.dto.review.ReviewResponse;
import com.routy.routyback.dto.review.ReviewUpdateRequest;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface IReviewService {

    ReviewListResponse getReviewList(int prdNo, int page, int limit, String sort, int userNo,Integer userSkin, Integer userColor); // 리뷰 목록 조회

    ReviewResponse createReview(int prdNo, ReviewCreateRequest request, List<MultipartFile> files); // 리뷰 저장

    ReviewResponse updateReview(int revNo, ReviewUpdateRequest request, List<MultipartFile> newFiles); // 리뷰 수정

    void deleteReview(int revNo); // 리뷰삭제

    ReviewLikeResponse toggleLike(int revNo, int userNo); // 리뷰 좋아요

    /**
     * 특정 리뷰에 연결된 이미지 경로 목록을 조회합니다.
     *
     * @param revNo 이미지 목록을 조회할 리뷰 번호
     * @return 해당 리뷰에 등록된 이미지 URL(또는 파일 경로) 리스트
     */
    List<String> getReviewImages(int revNo);

}
