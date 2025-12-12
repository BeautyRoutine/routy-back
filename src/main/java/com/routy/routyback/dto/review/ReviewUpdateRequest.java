package com.routy.routyback.dto.review;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewUpdateRequest {
	private int revStar;
    private String content;		//리뷰 텍스트
    private List<String> revImages;
    
    private List<Integer> feedback;
	
}
