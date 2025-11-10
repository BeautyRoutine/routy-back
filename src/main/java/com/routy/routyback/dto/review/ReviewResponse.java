package com.routy.routyback.dto.review;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewResponse {
    private int revNo;
    private String userName;
    private String revRank;
    private int revStar;
    private String revText;
    private String revImg;
    private String revDate;
    private int likeCount;
}
