/**
 * 주문 기본 정보 DTO
 * ORDERS 테이블과 1:1로 매핑되는 주문 데이터입니다.
 * @author 김지용
 */
package com.routy.routyback.dto.order;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class OrdersDTO {

    private Long odNo;          // 주문 번호(PK)
    private Long userNo;        // 회원 번호(FK)
    private String odName;      // 주문자 이름
    private String odHp;        // 주문자 연락처
    private Integer odZip;      // 우편번호
    private String odJibunAddr; // 지번 주소
    private String odRoadAddr;  // 도로명 주소
    private String odDetailAddr;   // 상세 주소
    private Long odBcCode;      // 법정동 코드
    private Integer odPrdPrice; // 상품 총 금액
    private Integer odDelvPrice;// 배송비
    private String odDelvMsg;   // 배송 요청 메시지
    private Integer odDelvKeyType; // 출입 방법 코드
    private String odDelvKey;   // 출입 번호/방법
    private LocalDateTime odRegdate; // 주문 일시
    private Integer odStatus;   // 주문 상태(1~7)
}