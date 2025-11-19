package com.routy.routyback.domain;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewVO {

    private int revNo;
    private int userNo;
    private int prdNo;
    private int revRank;
    private int revStar;
    private String revImg;
    private String revGood;
    private String revBad;
    private LocalDate revDate;

    private Integer odNo; // 주문번호 FK
    private String userName;
    private int likeCount;

    // 신뢰도 점수 / 등급
    private Double revTrustScore;   // REVIEW.REVTRUSTSCORE
    private String revTrustRank;    // REVIEW.REVTRUSTRANK

    // 리뷰 이미지 개수 (신뢰도 계산에 사용)
    private int photoCount;
}
