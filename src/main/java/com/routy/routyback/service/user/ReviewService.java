package com.routy.routyback.service.user;

import com.routy.routyback.dto.review.ReviewCreateRequest;
import com.routy.routyback.dto.review.ReviewLikeResponse;
import com.routy.routyback.dto.review.ReviewListResponse;
import com.routy.routyback.dto.review.ReviewResponse;
import com.routy.routyback.dto.review.ReviewUpdateRequest;

public class ReviewService implements IReviewService{

	@Override
	public ReviewListResponse getReviewList(int prdNo, int page, int limit, String sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReviewResponse createReview(int prdNo, ReviewCreateRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReviewResponse updateReview(int revNo, ReviewUpdateRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteReview(int revNo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ReviewLikeResponse toggleLike(int revNo, int userNo) {
		// TODO Auto-generated method stub
		return null;
	}

}
