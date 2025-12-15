package com.routy.routyback.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderClaimDTO {
	private Integer qnaNo;
	private Integer userNo;
	private Integer odNo;
	private Integer delvNo;
	private Integer qnaType; // 문의 유형 (1:제품 / 2:주문 / 3:리뷰신고 / 7:환불요청 /	8:교환요청)
	private String qnaQ;
	private String qnaA;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private Date qnaDate; // 작성일
	private Integer qnaStatus; // 1:등록/2:진행중/3:완료
	
	private String userName;
	private String userNick;
	private String userId;

}
