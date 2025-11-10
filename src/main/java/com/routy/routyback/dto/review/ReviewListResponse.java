package com.routy.routyback.dto.review;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class ReviewListResponse {
	  private SummaryDto summary; //평균 별점, 리뷰수
	    private List<ReviewResponse> reviews;  //리뷰 내역
	    private PaginationDto pagination; //페이징에 필요한 dto

	    

	    @Getter
	    @Setter
	    public static class SummaryDto {
	        private int totalCount;
	        private double averageRating;
	    }

	    @Getter
	    @Setter
	    public static class PaginationDto {
	        private int page;
	        private int limit;
	        private int totalCount;
	        private int totalPages;
	    }
}
