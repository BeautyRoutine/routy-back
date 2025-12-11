package com.routy.routyback.domain;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewVO {

    // 기본 리뷰 정보
    private int revNo;          // 리뷰 번호 (PK)
    private int userNo;         // 작성자 회원 번호 (FK → USERS)
    private int prdNo;          // 상품 번호 (FK → PRODUCT)
    private int revRank;        // 작성자 랭크 (가중치용)
    private int revStar;        // 별점 (1~5)
    private String content;		//리뷰 텍스트
    private LocalDate revDate;  // 작성일

    // 주문 / 유저 관련 정보
    private Integer odNo;       // 주문 번호 (구매 인증 여부 확인용, null이면 미인증)
    private String userName;    // 작성자 이름
    private int likeCount;      // 좋아요 개수

    // 이미지 관련
    private int photoCount;           // 리뷰 이미지 개수 (REVIEW_IMAGE COUNT(*))
    private String imageUrls;   // 리뷰 이미지 URL 리스트 (선택: 나중에 Mapper에서 채울 예정)
    // 리뷰 이미지 VO 리스트 (Service에서 필요 시 전체 객체 활용)
    private List<ReviewImageVO> images;

    // 신뢰도 점수 / 등급
    private Double revTrustScore;   // 리뷰 신뢰도 점수
    private String revTrustRank;    // 리뷰 신뢰도 티어 코드 (예: TIER_PREMIUM 등)
    
    //필드 추가 - 유저 피부톤, 피부타입
    private Integer userSkin;
    private Integer userColor;
}