package com.routy.routyback.domain;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewImageVO {

    private int riNo;       // PK (RI_NO)
    private int revNo;      // 리뷰번호 (REV_NO)
    private String riUrl;   // 이미지 URL (RI_URL)
    private int riSort;     // 노출 순서 (RI_SORT)
    private LocalDate riRegDate;
}