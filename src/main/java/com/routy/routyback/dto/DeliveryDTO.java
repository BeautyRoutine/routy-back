package com.routy.routyback.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryDTO {
	private int DELVNO;
	private int ODNO;
	private String DELVCOMPANY; // 택배사(50)
	private String DELVCOMNUM; // 운송장(20)
	private int DELVTYPE; // 택배타입(2) - 11:배송/12:재배송/13:취소//21:교환회수/22:교환재발송//31:반품회수
	private int DELVSTATUS; // 택배상태(2) - 1: 배송준비중, 2: 집화완료, 3: 배송중, 4: 지점 도착, 5: 배송출발, 6:배송 완료
	private String DELVGETNAME; // 수령인 명(100)
	private String DELVGETHP; // 수령인 연락처(13)
	private int DELVGETZIP; //수령지 우편번호(5)
	private String DELVGETJIBUNADDR; //수령지 지번 주소(255)
	private String DELVGETROADADDR; //수령지 도로명 주소(255)
	private String DELVGETDETAILADDR; //수령지 상세 주소(255)
	private long DELVGETBCCODE; // 수령지 시군구코드(12)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private Date DELVENDDATE; // 완료일
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private Date DELVREGDATE; // 접수일

}
