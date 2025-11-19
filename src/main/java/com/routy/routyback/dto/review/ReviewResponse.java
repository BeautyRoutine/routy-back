package com.routy.routyback.dto.review;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewResponse {

    private int revNo;
    private String userName;
    private int revRank;
    private int revStar;
    private String revGood;
    private String revBad;
    private String revImg;
    private String revDate;
    private int likeCount;
    private Double revTrustScore;   // 신뢰도 점수
    private String revTrustRank;    // 신뢰도 등급
    private int photoCount;         // 리뷰 이미지 개수
}
