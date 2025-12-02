package com.routy.routyback.service.user.mypage;

import com.routy.routyback.dto.user.mypage.UserReviewCreateRequest;
import com.routy.routyback.dto.user.mypage.UserReviewDetailResponse;
import com.routy.routyback.dto.user.mypage.UserReviewResponse;
import com.routy.routyback.dto.user.mypage.UserReviewUpdateRequest;
import com.routy.routyback.mapper.user.mypage.UserReviewMapper;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 나의 리뷰 서비스 구현체
 * - 비즈니스 로직 처리
 */
@Service
@RequiredArgsConstructor
public class UserReviewService implements IUserReviewService {

    // 나의 리뷰 관련 쿼리를 담당하는 Mapper
    private final UserReviewMapper userReviewMapper;

    @Override
    public void createReview(Long userNo, UserReviewCreateRequest req) {

        // 1) 리뷰 본문/별점/장단점 저장
        userReviewMapper.insertReview(
            userNo,
            req.getProductId(),
            req.getRating(),
            req.getContent(),
            req.getGood(),
            req.getBad()
        );

        // 2) 방금 작성한 리뷰 번호 조회 (REVIEW_SEQ 기반)
        Long reviewNo = userReviewMapper.selectLastInsertedReviewNo(userNo);

        // 3) 리뷰 이미지가 존재한다면 개별 insert 처리
        if (req.getImages() != null) {
            for (String url : req.getImages()) {
                userReviewMapper.insertReviewImage(reviewNo, url);
            }
        }
    }

    /**
     * 나의 리뷰 목록 조회
     */
    @Override
    public List<UserReviewResponse> getUserReviews(Long userNo) {
        if (userNo == null) {
            return Collections.emptyList();
        }
        return userReviewMapper.selectUserReviews(userNo);
    }

    /**
     * 나의 리뷰 상세 조회
     */
    @Override
    public UserReviewDetailResponse getReviewDetail(Long userNo, Long reviewId) {
        if (userNo == null || reviewId == null) {
            return null;
        }

        UserReviewDetailResponse detail =
            userReviewMapper.selectUserReviewDetail(userNo, reviewId);

        if (detail == null) {
            return null;
        }

        // 이미지 목록 추가 조회
        List<String> images = userReviewMapper.selectReviewImages(reviewId);
        detail.setImages(images != null ? images : Collections.emptyList());

        return detail;
    }

    /**
     * 리뷰 수정
     */
    @Override
    @Transactional
    public boolean updateReview(Long userNo, Long reviewId, UserReviewUpdateRequest request) {
        if (userNo == null || reviewId == null || request == null) {
            return false;
        }

        int updated = userReviewMapper.updateUserReview(
            userNo,
            reviewId,
            request.getRating(),
            request.getGood(),
            request.getBad(),
            request.getContent()
        );

        // updated == 0 이면 해당 리뷰가 없거나 본인 것이 아님
        return updated > 0;
    }

    /**
     * 리뷰 삭제 (이미지 → 리뷰 순)
     */
    @Override
    @Transactional
    public boolean deleteReview(Long userNo, Long reviewId) {
        if (userNo == null || reviewId == null) {
            return false;
        }

        // 먼저 이미지 삭제
        userReviewMapper.deleteReviewImages(reviewId);

        // 본인 리뷰만 삭제
        int deleted = userReviewMapper.deleteUserReview(userNo, reviewId);

        return deleted > 0;
    }
}