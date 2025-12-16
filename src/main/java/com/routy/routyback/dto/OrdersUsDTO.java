package com.routy.routyback.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdersUsDTO {
	private int odNo;          // 주문 번호(PK)
    private int userNo;        // 회원 번호(FK)
    private String odName;      // 주문자 이름
    private String odHp;        // 주문자 연락처
    private int odZip;      // 우편번호
    private String odJibunAddr; // 지번 주소
    private String odRoadAddr;  // 도로명 주소
    private String odDetailAddr;   // 상세 주소
    private Long odBcCode;      // 법정동 코드
    private int odPrdPrice; // 상품 총 금액
    private int odDelvPrice;// 배송비
    private String odDelvMsg;   // 배송 요청 메시지
    private int odDelvKeyType; // 출입 방법 코드
    private String odDelvKey;   // 출입 번호/방법
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date odRegdate; // 주문 일시
    private int odStatus;   // 주문 상태 (1:주문서/2:결제완료/3:준비중/4:배송중/5:완료/6:취소/7:환불/8:교환)
	
 // OrdersAdmMapper.xml - listAllOrders, detailOrder 사용
	private String userId;
	private String userNick;
	private String userName;
	private String userHp;

}
