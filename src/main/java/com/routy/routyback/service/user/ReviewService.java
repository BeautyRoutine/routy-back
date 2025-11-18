package com.routy.routyback.service.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.routy.routyback.domain.ReviewVO;
import com.routy.routyback.dto.review.ReviewCreateRequest;
import com.routy.routyback.dto.review.ReviewLikeResponse;
import com.routy.routyback.dto.review.ReviewListResponse;
import com.routy.routyback.dto.review.ReviewListResponse.PaginationDto;
import com.routy.routyback.dto.review.ReviewListResponse.SummaryDto;
import com.routy.routyback.dto.review.ReviewResponse;
import com.routy.routyback.dto.review.ReviewUpdateRequest;
import com.routy.routyback.mapper.user.ReviewMapper;

@Service("reviewService")
public class ReviewService implements IReviewService {

    @Autowired
    ReviewMapper reviewMapper;

    @Override
    public ReviewListResponse getReviewList(int prdNo, int page, int limit, String sort) {
        // 맵 구성
        Map<String, Object> params = new HashMap<>();
        params.put("prdNo", prdNo);
        params.put("offset", (page - 1) * limit); // 1페이지면 0개, 2페이지면 10개 건너뛰기
        params.put("limit", limit);  // 1페이지 리뷰 수
        params.put("sort", sort); // 정렬 기준

        // mapper 가져오기
        List<ReviewVO> reviewVOs = reviewMapper.findReviews(params); // 실제 리뷰 목록
        int totalCount = reviewMapper.countReviews(prdNo); // 전체 리뷰수
        Double avgRating = reviewMapper.findAverageRating(prdNo); // 평균 별점

        // vo를 dto로 변환
        List<ReviewResponse> reviewResponses = new ArrayList<>();
        for (ReviewVO vo : reviewVOs) {
            ReviewResponse dto = convertVoToResponseDto(vo); // vo를 dto로 변환
            reviewResponses.add(dto); // reviewResponses에 받아온 dto 넣기
        }

        // summary 생성(총 별점, 리뷰수 측정)
        SummaryDto summary = new SummaryDto(); // 객체 생성
        summary.setTotalCount(totalCount); // 리뷰수 측정
        summary.setAverageRating(avgRating != null ? Math.round(avgRating * 10.0) / 10.0 : 0.0);
        // null이면 0.0, Math.round로 소수점 반올림

        // 페이징 생성
        PaginationDto pagination = new PaginationDto(); // 객체 생성
        pagination.setPage(page); // 페이지 정보
        pagination.setLimit(limit); // 페이지당 리뷰 수 제한
        pagination.setTotalCount(totalCount); // 전체 리뷰 수
        pagination.setTotalPages((int) Math.ceil((double) totalCount / limit));
        // 전체 페이지 수 계산: (전체 개수 / 페이지당 개수)를 올림(ceil) 처리(반올림은 안됨)

        // 최종객체 만들기
        ReviewListResponse finalResponse = new ReviewListResponse(); // finalResponse 만들어서 거기에 전부 넣기
        finalResponse.setSummary(summary);
        finalResponse.setReviews(reviewResponses);
        finalResponse.setPagination(pagination);

        // 완성 객체 반환
        return finalResponse;
    }

    /***
     * 리뷰 작성 관련 내용
     * 작성자 : 박민우, 김지용
     * @param prdNo
     * @param request
     * @return responseDTO
     */
    @Override
    public ReviewResponse createReview(int prdNo, ReviewCreateRequest request) {

        // 1) VO 객체 생성 후 , 요청값 세팅
        ReviewVO reviewVO = new ReviewVO(); // vo 준비
        reviewVO.setPrdNo(prdNo); // 컨트롤러로 받은 prdNo
        reviewVO.setUserNo(request.getUserNo()); // request로 받은 dto들
        reviewVO.setRevStar(request.getRevStar());  // 별점
        reviewVO.setRevGood(request.getRevGood());
        reviewVO.setRevBad(request.getRevBad());
        reviewVO.setRevImg(request.getRevImg());

        // 2) 리뷰 기본 정보 저장(REVNO 생성)
        reviewMapper.insertReview(reviewVO); // vo를 통해 mapper 호출해서 insert 실행

        // 3) 방금 저장된 리뷰 다시 조회(odNo, userName 등 포함)
        ReviewVO createdReviewVO = reviewMapper.findReview(reviewVO.getRevNo());

        // 4) 신뢰도 점수 계산 및 등급 부여
        double trustScore = calculateTrustScore(createdReviewVO); // 길이 + 구매 인증 기준으로 점수 계산
        String trustRank = calculateTrustRank(trustScore); // 점수 기준으로 등급 계산

        // 5) VO에 계산된 값 세팅
        createdReviewVO.setRevTrustScore(trustScore);   // REVIEW.REVTRUSTSCORE에 들어갈 값
        createdReviewVO.setRevTrustRank(trustRank); // REVIEW.REVTRUSTRANK에 들어갈 값

        // 6) DB에 신뢰도 점수/등급 업데이트
        reviewMapper.updateReviewTrustScore(createdReviewVO);

        // 7) 클라이언트로 내려줄 DTO로 변환
        ReviewResponse responseDTO = convertVoToResponseDto(createdReviewVO); // vo를 dto로 변환

        // 응답 반환
        return responseDTO;
    }

    @Override
    public ReviewResponse updateReview(int revNo, ReviewUpdateRequest request) {
        ReviewVO reviewVO = new ReviewVO(); // vo 준비
        reviewVO.setRevNo(revNo); // revNo 지정
        reviewVO.setRevStar(request.getRevStar());
        reviewVO.setRevGood(request.getRevGood());
        reviewVO.setRevBad(request.getRevBad());
        reviewVO.setRevImg(request.getRevImg()); // 리뷰 수정한 값을 vo에 저장

        reviewMapper.updateReview(reviewVO);// vo를 통해 mapper 호출해서 update 실행

        ReviewVO updatedReviewVO = reviewMapper.findReview(revNo);// 방금 만든 리뷰 다시 조회

        return convertVoToResponseDto(updatedReviewVO); // 바로 반환
    }

    @Override
    public void deleteReview(int revNo) {
        reviewMapper.deleteReview(revNo); // delete 실행

    }

    @Override
    public ReviewLikeResponse toggleLike(int revNo, int userNo) {
        Integer likeExists = reviewMapper.findLikeByUserAndReview(revNo, userNo);
        // 유저가 이 리뷰에 좋아요 눌렀는지 db 확인, Integer인 이유
        if (likeExists != null) { // 눌렀으면
            reviewMapper.deleteLike(revNo, userNo); // 삭제
        } else { // 안눌렀으면
            reviewMapper.insertLike(revNo, userNo);  // 추가
        }
        int currentLikeCount = reviewMapper.countLikes(revNo); // 리뷰 좋아요 수 다시 조회

        return new ReviewLikeResponse(revNo, currentLikeCount); //@AllArgsConstructor 생성자 사용해 바로 객체 생성 후 반환
    }


    private ReviewResponse convertVoToResponseDto(ReviewVO vo) {
        if (vo == null) { // null 뜨면 null 반환
            return null;
        }
        ReviewResponse dto = new ReviewResponse(); // 빈 객체 생성
        dto.setRevNo(vo.getRevNo());
        dto.setUserName(vo.getUserName());
        dto.setRevRank(vo.getRevRank());
        dto.setRevStar(vo.getRevStar());
        dto.setRevGood(vo.getRevGood());
        dto.setRevBad(vo.getRevBad());
        dto.setRevImg(vo.getRevImg());
        dto.setLikeCount(vo.getLikeCount()); // vo 값들 가져다가 dto에 넣어주기
        if (vo.getRevDate() != null) {
            dto.setRevDate(vo.getRevDate().toString()); // "yyyy-MM-dd" 형식 반환
        }

        return dto;
    }

    /***
     * 리뷰 신뢰도 점수 계산
     * 작성자 : 김지용
     * @param review
     * @return reviewScore
     */
    private double calculateTrustScore(ReviewVO review) {

        // 1) 구매 인증 여부
        boolean isVerifiedPurchase = review.getOdNo() != null;

        // 2) 리뷰 길이로 간단한 품질 측정
        int reviewLength = 0;
        if (review.getRevGood() != null) {
            reviewLength += review.getRevGood().length();
        }
        if (review.getRevBad() != null) {
            reviewLength += review.getRevBad().length();
        }

        // 3) 가중치 기본값
        double reviewScore = 1.0; // 일반 후기 기본 값

        if (!isVerifiedPurchase) {
            return 0.1; // 미인증 후기
        }

        if (isVerifiedPurchase && reviewLength > 200) {
            reviewScore = 2.0; // 프리미엄
        } else if (isVerifiedPurchase && reviewLength > 50) {
            reviewScore = 1.5; // 우수
        } else if (isVerifiedPurchase) {
            reviewScore = 1.0; // 일반
        }

        return reviewScore;

    }


    // 점수 구간에 따른 등급 이름 매핑 - 작성자 : 김지용
    private String calculateTrustRank(double score) {
        if (score >= 2.0) {
            return "프리미엄 후기";
        }
        if (score >= 1.5) {
            return "우수 후기";
        }
        if (score >= 1.0) {
            return "일반 후기";
        }
        if (score >= 0.7) {
            return "주의 후기";
        }
        if (score >= 0.5) {
            return "의심 후기";
        }
        return "미인증 후기";
    }


}
