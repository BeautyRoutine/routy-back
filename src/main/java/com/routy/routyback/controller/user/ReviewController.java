package com.routy.routyback.controller.user;

import com.routy.routyback.dto.review.ReviewCreateRequest;
import com.routy.routyback.dto.review.ReviewLikeResponse;
import com.routy.routyback.dto.review.ReviewListResponse;
import com.routy.routyback.dto.review.ReviewResponse;
import com.routy.routyback.dto.review.ReviewUpdateRequest;
import com.routy.routyback.service.user.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // restapi
@RequestMapping("/api")
public class ReviewController {


    @Autowired
    @Qualifier("reviewService")
    IReviewService service;

    // 조회
    @GetMapping("/products/{prdNo}/reviews")
    public ResponseEntity<ReviewListResponse> getReviews( // ReviewListResponse를 반환
        @PathVariable int prdNo,  // URL 경로 값을 가져옴, 필수, prdNo 받아옴
        @RequestParam(defaultValue = "1") int page, // 기본값 쿼리 파라미터 설정, ?page=1 등
        @RequestParam(defaultValue = "10") int limit, //
        @RequestParam(defaultValue = "latest") String sort
    ) {
        ReviewListResponse responseData = service.getReviewList(prdNo, page, limit, sort);
        return ResponseEntity.ok(responseData); // ok 응답(성공 200 ok)
    }

    // 리뷰 작성
    @PostMapping("/products/{prdNo}/reviews")
    public ResponseEntity<ReviewResponse> createReview(  // ReviewResponse 반환
        @PathVariable int prdNo,
        @RequestBody ReviewCreateRequest request) { // json 받아와서 request 객체로 만들어줌
        ReviewResponse createReview = service.createReview(prdNo, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createReview); // 응답번호, createReview 반환
    }

    // 리뷰수정
    @PutMapping("/reviews/{revNo}")
    public ResponseEntity<ReviewResponse> updateReview(
        @PathVariable int revNo,
        @RequestBody ReviewUpdateRequest request) {
        ReviewResponse updateReview = service.updateReview(revNo, request);
        return ResponseEntity.ok(updateReview); // 수정이니까 ok 200
    }

    // 리뷰 삭제
    @DeleteMapping("/reviews/{revNo}")
    public ResponseEntity<Void> deleteReview(  // 삭제니까 반환할게 void
        @PathVariable int revNo
    ) {
        service.deleteReview(revNo);
        return ResponseEntity.noContent().build(); // 204, 성공,내용물 없음 반환, build로 구성
    }

    ;

    // 리뷰 좋아요
    @PostMapping("/reviews/{revNo}/like")
    public ResponseEntity<ReviewLikeResponse> toggleLike(
        @PathVariable int revNo
    ) {
        int userNo = 1; // 임시

        ReviewLikeResponse response = service.toggleLike(revNo, userNo);
        return ResponseEntity.ok(response);
    }

}
