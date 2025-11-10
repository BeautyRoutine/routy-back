package com.routy.routyback.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor //서비스에서 dto 객체 간단하게 생성(new ReviewLikeResponse(revNo,likeCount)
public class ReviewLikeResponse {
	private int reVno;
	private int likeCount;
}
