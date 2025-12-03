package com.routy.routyback.dto;

import java.util.Date;

import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ToString
public class ProductDTO {

    private int prdNo;
    private String prdName;
    private int prdPrice;
    private int prdVolume;
    private String prdCompany;
    private int prdStock;
    private int prdMainCate;
    private int prdSubCate;
    private String prdImg;
    private String prdDesc;

    // 날짜 포맷 설정 (Form 입력 + JSON 변환용)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date prdUpdate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date prdRegDate;

}
