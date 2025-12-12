package com.routy.routyback.dto.user.mypage;

import lombok.Data;

/**
 * 사용자 좋아요(찜) 상품 조회 응답 DTO
 */
@Data
public class UserLikeResponse {

    /**
     * 좋아요 ID
     */
    private Long likeId;

    /**
     * 상품 번호
     */
    private Long productId;

    /**
     * 프런트 연결용
     */
    private Long prdNo;

    /**
     * 상품 이름
     */
    private String productName;

    /**
     * 상품 제조 또는 브랜드명
     */
    private String productCompany;

    /**
     * 상품 가격
     */
    private Integer price;

    /**
     * 상품 이미지
     */
    private String imageUrl;

    /**
     * 좋아요 등록일
     */
    private String regDate;

    public void setPrdNo(Long prdNo) {
        this.prdNo = prdNo;
        this.productId = prdNo;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
        this.prdNo = productId;
    }
}