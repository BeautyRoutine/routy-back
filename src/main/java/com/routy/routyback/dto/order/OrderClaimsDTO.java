package com.routy.routyback.dto.order;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderClaimsDTO {

	private Integer qnaNo;
	private Integer userNo;
	private Integer odNo;
	
	private Integer qnaType;	// 문의 유형 (1:제품 / 2:주문 / 3:리뷰신고 / 5:환불요청 / 6:교환요청)
	private String qnaQ;
	private String qnaA;
	private Integer qnaStatus;	//1:등록/2:진행중/3:완료
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private Date qnaDate;
	
	private Integer odStatus;
	
}
