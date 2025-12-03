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
    private final com.routy.routyback.mapper.user.UserMapper userMapper;

    @Override
    public void createReview(String userId, UserReviewCreateRequest req) {
        Long resolvedUserNo = userMapper.findUserNoByUserId(userId);
        if (resolvedUserNo == null) {
            return;
        }

        // 1) 리뷰 본문/별점/장단점 저장
        userReviewMapper.insertReview(
            resolvedUserNo,
            req.getProductId(),
            req.getRating(),
            req.getContent(),
            req.getGood(),
            req.getBad()
        );

        // 2) 방금 작성한 리뷰 번호 조회 (REVIEW_SEQ 기반)
        Long reviewNo = userReviewMapper.selectLastInsertedReviewNo(resolvedUserNo);

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
    public List<UserReviewResponse> getUserReviews(String userId) {
        Long resolvedUserNo = userMapper.findUserNoByUserId(userId);
        if (resolvedUserNo == null) {
            return Collections.emptyList();
        }
        return userReviewMapper.selectUserReviews(resolvedUserNo);
    }

    /**
     * 나의 리뷰 상세 조회
     */
    @Override
    public UserReviewDetailResponse getReviewDetail(String userId, Long reviewId) {
        Long resolvedUserNo = userMapper.findUserNoByUserId(userId);
        if (resolvedUserNo == null || reviewId == null) {
            return null;
        }

        UserReviewDetailResponse detail =
            userReviewMapper.selectUserReviewDetail(resolvedUserNo, reviewId);

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
    public boolean updateReview(String userId, Long reviewId, UserReviewUpdateRequest request) {
        Long resolvedUserNo = userMapper.findUserNoByUserId(userId);
        if (resolvedUserNo == null || reviewId == null || request == null) {
            return false;
        }

        int updated = userReviewMapper.updateUserReview(
            resolvedUserNo,
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
    public boolean deleteReview(String userId, Long reviewId) {
        Long resolvedUserNo = userMapper.findUserNoByUserId(userId);
        if (resolvedUserNo == null || reviewId == null) {
            return false;
        }

        // 먼저 이미지 삭제
        userReviewMapper.deleteReviewImages(reviewId);

        // 본인 리뷰만 삭제
        int deleted = userReviewMapper.deleteUserReview(resolvedUserNo, reviewId);

        return deleted > 0;
    }
}