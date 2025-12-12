package com.routy.routyback.dto.user.myrouty;

import lombok.Data;

@Data
public class UsedProductDetail {

    private Long prdNo;        // 제품 번호
    private String prdName;    // 제품 이름
    private String prdImg;     // 제품 이미지 경로

    private String reaction;   // GOOD / BAD / NORMAL
    private String memo;       // 메모
    private String alertDate;  // 재사용 알림 날짜 (yyyy-MM-dd)
}