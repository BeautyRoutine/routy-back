package com.routy.routyback.controller.user;

import com.routy.routyback.dto.review.ReviewCreateRequest;
import com.routy.routyback.dto.review.ReviewLikeResponse;
import com.routy.routyback.dto.review.ReviewListResponse;
import com.routy.routyback.dto.review.ReviewResponse;
import com.routy.routyback.dto.review.ReviewUpdateRequest;
import com.routy.routyback.service.user.IReviewService;
import java.util.List;
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

/**
 * 리뷰 관련 REST API를 제공하는 컨트롤러입니다. - 상품별 리뷰 목록 조회 - 리뷰 작성 / 수정 / 삭제 - 리뷰 좋아요(추천) 토글
 * <p>
 * 화면(프론트엔드)에서 호출하는 진입점 역할을 합니다.
 */
@RestController // restapi
@RequestMapping("/api")
public class ReviewController {


    @Autowired
    @Qualifier("reviewService")
    IReviewService service;

    // 리뷰 목록 조회
    @GetMapping("/products/{prdNo}/reviews")
    public ResponseEntity<ReviewListResponse> getReviews( // ReviewListResponse 전체 응답 DTO를 반환
        @PathVariable int prdNo,  // URL 경로에서 상품 번호(prdNo)를 가져옴. 예: /products/10/reviews → prdNo=10
        @RequestParam(defaultValue = "1") int page, // 쿼리 파라미터 ?page=값, 기본값은 1페이지
        @RequestParam(defaultValue = "10") int limit, // 쿼리 파라미터 ?limit=값, 한 페이지에 보여줄 리뷰 개수, 기본값 10개
        @RequestParam(defaultValue = "recommended") String sort // 정렬 기준. 기본값은 추천순(신뢰도 기반)
    ) {
        // 서비스 레이어에 조회 요청. 정렬/페이징 옵션까지 함께 전달
        ReviewListResponse responseData = service.getReviewList(prdNo, page, limit, sort);

        // HTTP 200 OK 상태코드와 함께 조회된 리뷰 목록/요약/페이징 정보를 반환
        return ResponseEntity.ok(responseData);
    }

    // 리뷰 작성
    @PostMapping("/products/{prdNo}/reviews")
    public ResponseEntity<ReviewResponse> createReview(  // 성공 시 생성된 단일 리뷰 정보(ReviewResponse)를 반환
        @PathVariable int prdNo, // 어떤 상품에 대한 리뷰인지 식별하기 위한 상품 번호
        @RequestBody ReviewCreateRequest request) { // JSON 요청 바디를 ReviewCreateRequest DTO로 매핑해서 받음
        // 서비스에 리뷰 생성 요청. 서비스 내부에서 신뢰도 점수 계산, DB 저장까지 처리
        ReviewResponse createReview = service.createReview(prdNo, request);

        // HTTP 201 Created 상태코드와 함께 생성된 리뷰 정보를 응답 바디에 담아 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(createReview);
    }

    // 리뷰 수정
    @PutMapping("/reviews/{revNo}")
    public ResponseEntity<ReviewResponse> updateReview(
        @PathVariable int revNo, // 어떤 리뷰를 수정할지 식별하기 위한 리뷰 번호
        @RequestBody ReviewUpdateRequest request) { // 수정할 내용(별점, 내용, 이미지 등)을 담은 DTO
        // 서비스에 수정 요청. 내부에서 DB update 후 수정된 내용을 다시 조회해 DTO로 변환
        ReviewResponse updateReview = service.updateReview(revNo, request);

        // HTTP 200 OK와 함께 수정된 리뷰 정보를 반환
        return ResponseEntity.ok(updateReview);
    }

    // 리뷰 삭제
    @DeleteMapping("/reviews/{revNo}")
    public ResponseEntity<Void> deleteReview(  // 삭제는 별도의 응답 데이터가 필요 없으므로 제네릭 타입을 Void로 사용
        @PathVariable int revNo // 삭제할 리뷰 번호
    ) {
        // 서비스에 삭제 요청. 내부에서 존재 여부 확인 후 DB에서 삭제 수행
        service.deleteReview(revNo);

        // HTTP 204 No Content: 요청은 성공했지만, 돌려줄 응답 바디는 없음
        return ResponseEntity.noContent().build();
    }

    // 리뷰 좋아요(추천) 토글
    @PostMapping("/reviews/{revNo}/like")
    public ResponseEntity<ReviewLikeResponse> toggleLike(
        @PathVariable int revNo // 좋아요를 토글할 대상 리뷰 번호
    ) {
        // TODO: 나중에 로그인 기능이 붙으면 실제 로그인된 사용자 번호를 주입받도록 수정해야 함
        int userNo = 1; // 임시 고정 사용자 번호. 현재는 로그인 기능이 없기 때문에 하드코딩

        // 서비스에 좋아요 토글 요청
        // - 이미 좋아요를 눌렀으면 좋아요 취소
        // - 안 눌렀으면 새로 좋아요 추가
        // 처리 후 현재 좋아요 개수와 함께 응답 DTO 반환
        ReviewLikeResponse response = service.toggleLike(revNo, userNo);

        // HTTP 200 OK와 함께 현재 좋아요 정보(리뷰 번호, 좋아요 수)를 반환
        return ResponseEntity.ok(response);
    }

    // 특정 리뷰의 이미지 목록 조회 - 작성자 : 김지용
    @GetMapping("/reviews/{revNo}/images")
    public ResponseEntity<List<String>> getReviewImages(
        @PathVariable int revNo // 이미지 조회할 리뷰 번호
    ) {
        // 서비스 레이어에서 이미지 목록 조회
        List<String> images = service.getReviewImages(revNo);

        // HTTP 200 OK와 함께 이미지 URL 리스트 반환
        return ResponseEntity.ok(images);
    }


}
