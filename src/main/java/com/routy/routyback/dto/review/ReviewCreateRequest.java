package com.routy.routyback.dto.review;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCreateRequest {
    private int userNo;
    private int prdNo;
    private int revStar;
    private String revGood;
    private String revBad;
    private String revImg;
}
