/**
 * 주문 목록 조회 응답 DTO
 * 마이페이지 주문 리스트 화면에서 사용하는 간단 주문 정보입니다.
 * 대표 상품명/이미지, 주문 상태, 상품 개수 등을 포함합니다.
 * @author 김지용
 */
package com.routy.routyback.dto.order;

import lombok.Data;

@Data
public class OrderListItemResponse {

    private Long orderNo;				// 주문 번호
    
    private Long productNo;				// 상품 번호
    private String productName;			// 상품명
    private String productImage;		// 이미지
    
    private Long ppMapNo;				// 결제상품 목록 PK
    private Long prodcutStock;			// 결제상품 갯수
    private Long productPrice;			// 결제상품 가격
    
    private Long reviewCnt;				// 리뷰 작성여부 체크
    private Long returnCnt;				// 환불 진행여부 체크
    private Long swapCnt;				// 교환 진행여부 체크
    
    private String orderDate;			// 주문 일자 (YYYY-MM-DD)
    private Integer orderStatus;		// 주문 상태 코드
}