package com.routy.routyback.service.user;

import org.springframework.stereotype.Component;
import com.routy.routyback.domain.ReviewVO;


/**
 * 리뷰 신뢰도 계산기 Review Tr용ust Score & Tier Calculator
 */
@Component
public class ReviewTrustCalculator {

    /**
     * 리뷰 신뢰도 점수 계산 (가중치 방식)
     */
    public double calculateTrustScore(ReviewVO review) {

        double score = 0.0;

        // 1) 구매 인증 여부
        score += calculatePurchaseWeight(review.getOdNo());

        // 2) 텍스트 길이 점수
        score += calculateTextWeight(review.getRevGood(), review.getRevBad());

        // 3) 사진 개수 점수
        score += calculatePhotoWeight(review.getPhotoCount());

        return Math.round(score * 100.0) / 100.0; // 소수점 2자리 반올림
    }

    /**
     * 구매 인증 가중치
     */
    private double calculatePurchaseWeight(Integer odNo) {
        if (odNo == null) {
            return 0.1; // 미인증
        }
        return 0.7; // 인증됨
    }

    /**
     * 텍스트 가중치 (good + bad 2개 합산)
     */
    private double calculateTextWeight(String good, String bad) {

        int length = 0;
        if (good != null) {
            length += good.length();
        }
        if (bad != null) {
            length += bad.length();
        }

        if (length >= 200) {
            return 1.0;
        }
        if (length >= 100) {
            return 0.8;
        }
        if (length >= 50) {
            return 0.5;
        }
        if (length >= 20) {
            return 0.3;
        }
        return 0.1;
    }

    /**
     * 사진 개수 가중치
     */
    private double calculatePhotoWeight(int count) {
        if (count >= 5) {
            return 0.5;
        }
        if (count >= 4) {
            return 0.4;
        }
        if (count >= 3) {
            return 0.3;
        }
        if (count >= 2) {
            return 0.2;
        }
        if (count >= 1) {
            return 0.1;
        }
        return 0.0;
    }

    /**
     * 리뷰 등급(Tier) 산출 FE 노출 X — 내부 레벨링용
     */
    public String calculateTrustRank(double score, boolean isVerified) {

        // 미인증이면 자동 하위 등급 고정
        if (!isVerified) {
            if (score >= 0.7) {
                return "TIER_LOW";
            }
            if (score >= 0.5) {
                return "TIER_SUSPICIOUS";
            }
            return "TIER_UNVERIFIED";
        }

        // 인증됨 + 점수 기준 적용
        if (score >= 2.0) {
            return "TIER_PREMIUM";
        }
        if (score >= 1.5) {
            return "TIER_HIGH";
        }
        if (score >= 1.0) {
            return "TIER_NORMAL";
        }
        if (score >= 0.7) {
            return "TIER_LOW";
        }
        if (score >= 0.5) {
            return "TIER_SUSPICIOUS";
        }
        return "TIER_UNVERIFIED";
    }
}