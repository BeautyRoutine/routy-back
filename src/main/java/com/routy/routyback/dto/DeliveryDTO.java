package com.routy.routyback.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryDTO {
	private int delvNo;
	private int odNo;
	private String delvCompany; // 택배사(50)
	private String delvComNum; // 운송장(20)
	private int delvType; // 택배타입(2) - 11:배송/12:재배송/13:취소//21:교환회수/22:교환재발송//31:반품회수
	private int delvStatus; // 택배상태(2) - 1: 배송준비중, 2: 집화완료, 3: 배송중, 4: 지점 도착, 5: 배송출발, 6:배송 완료
	private String delvGetName; // 수령인 명(100)
	private String delvGetHp; // 수령인 연락처(13)
	private int delvGetZip; //수령지 우편번호(5)
	private String delvGetJibunAddr; //수령지 지번 주소(255)
	private String delvGetRoadAddr; //수령지 도로명 주소(255)
	private String delvGetDetailAddr; //수령지 상세 주소(255)
	private long delvGetBcCode; // 수령지 시군구코드(12)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private Date delvEndDate; // 완료일
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private Date delvRegDate; // 접수일

}
