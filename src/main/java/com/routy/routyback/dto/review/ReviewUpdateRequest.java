package com.routy.routyback.dto.review;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewUpdateRequest {
	private int revStar;
	private String revGood;
	private String revBad;
	private String revImg;
	
}
