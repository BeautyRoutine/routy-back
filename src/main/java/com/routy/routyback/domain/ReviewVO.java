package com.routy.routyback.domain;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewVO {
	 private int revNo;
	 private int userNo;
	 private int prdNo;
	 private int revRank;
	 private int revStar;
	 private String revImg;
	 private String revGood; 
	 private String revBad;  
	 private LocalDate revDate;
	 
	 private String userName;
	 private int likeCount;
}
