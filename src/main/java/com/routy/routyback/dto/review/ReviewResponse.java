package com.routy.routyback.dto.review;

import java.util.List;

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
    // 리뷰 이미지 URL 리스트 (정렬된 순서대로 FE에 전달)
    private List<String> images;
}
