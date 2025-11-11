package com.routy.routyback.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class IngredientDTO {
	private int ingNo; // 성분 번호 (PK)
	private String ingName; // 성분 한글 이름
	private String ingEnName; // 성분 영어 이름
	private String ingDesc; // 성분 설명
	private int ingAllergen; // 알레르기 여부 (0/1)
	private int ingDanger; // 주의 성분 여부 (0/1)
	private String ingFunctional; // 기능성 (예: 여드름 완화)
	private Integer ingGrpNo; // 그룹 번호 (nullable)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private Date ingRegDate; // 등록일 (CURRENT_TIMESTAMP)


}
