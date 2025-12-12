package com.routy.routyback.dto.review;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCreateRequest {

    private int userNo;
    private int prdNo;
    private int revStar;
    private String content;		//리뷰 텍스트
    private String revImg;  // 단일 대표 이미지

    // 추가된 필드 - 작성자 : 김지용
    private Integer odNo; // 구매 인증용 주문 번호 ( 없으면 NULL )
    private int photoCount; // 첨부된 사진의 갯수 (0~5)
    
  //필드 추가 - 유저 피부톤, 피부타입
    private Integer userSkin;
    private Integer userColor;

}
