package com.routy.routyback.service.user;

import com.routy.routyback.domain.ReviewVO;
import com.routy.routyback.dto.review.ReviewCreateRequest;
import com.routy.routyback.dto.review.ReviewLikeResponse;
import com.routy.routyback.dto.review.ReviewListResponse;
import com.routy.routyback.dto.review.ReviewListResponse.PaginationDto;
import com.routy.routyback.dto.review.ReviewListResponse.SummaryDto;
import com.routy.routyback.dto.review.ReviewResponse;
import com.routy.routyback.dto.review.ReviewUpdateRequest;
import com.routy.routyback.mapper.user.ReviewMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("reviewService")
@Transactional
public class ReviewService implements IReviewService {

    @Autowired
    ReviewMapper reviewMapper;

    @Autowired
    ReviewTrustCalculator reviewTrustCalculator;

    @Override
    public ReviewListResponse getReviewList(int prdNo, int page, int limit, String sort, int userNo) {
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
            vo.setImages(reviewMapper.findReviewImages(vo.getRevNo()));
            ReviewResponse dto = convertVoToResponseDto(vo); // vo를 dto로 변환
            Integer checkLike = reviewMapper.findLikeByUserAndReview(vo.getRevNo(), userNo);
            dto.setLiked(checkLike != null);
            dto.setFeedback(new ArrayList<>());
            reviewResponses.add(dto); // reviewResponses에 받아온 dto 넣기
        }

        // summary 생성(총 별점, 리뷰수 측정)
        SummaryDto summary = new SummaryDto(); // 객체 생성
        summary.setTotalCount(totalCount); // 리뷰수 측정
        summary.setAverageRating(avgRating != null ? Math.round(avgRating * 10.0) / 10.0 : 0.0);
        // null이면 0.0, Math.round로 소수점 반올림

        //별점 분포 계산
        List<Map<String, Object>> starCounts = reviewMapper.countRatingByStars(prdNo);
        Map<Integer, Integer> distribution = new HashMap<>();
        // 1~5점 0으로 초기화
        for(int i=1; i<=5; i++) distribution.put(i, 0);
        // DB 결과 채워넣기
        for (Map<String, Object> map : starCounts) {
            // BigDecimal 등의 타입 문제 방지를 위해 String 변환 후 파싱
            int star = Integer.parseInt(String.valueOf(map.get("REVSTAR")));
            int count = Integer.parseInt(String.valueOf(map.get("CNT")));
            distribution.put(star, count);
        }
        summary.setDistribution(distribution); // DTO에 넣기
        
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

        // 1) VO 생성 및 기본값 세팅
        ReviewVO reviewVO = new ReviewVO();
        reviewVO.setPrdNo(prdNo);
        reviewVO.setUserNo(request.getUserNo());
        reviewVO.setRevStar(request.getRevStar());
        reviewVO.setContent(request.getContent());

        // 2) 리뷰 저장 → REVNO 생성됨
        reviewMapper.insertReview(reviewVO);

        // 3) 생성된 리뷰 재조회 (odNo, userName, photoCount 포함)
        ReviewVO created = reviewMapper.findReview(reviewVO.getRevNo());
        created.setImages(reviewMapper.findReviewImages(created.getRevNo()));

        // 4) 신뢰도 점수 계산
        double score = reviewTrustCalculator.calculateTrustScore(created);

        // 5) 등급 계산 (미인증이면 내부적으로 LOW 제한)
        String rank = reviewTrustCalculator.calculateTrustRank(score,
            created.getOdNo() != null);

        // 6) VO에 계산값 적용
        created.setRevTrustScore(score);
        created.setRevTrustRank(rank);

        // 7) DB 업데이트
        reviewMapper.updateReviewTrustScore(created);

        // 8) Response DTO 변환 후 반환
        return convertVoToResponseDto(created);
    }

    @Override
    public ReviewResponse updateReview(int revNo, ReviewUpdateRequest request) {
        ReviewVO reviewVO = new ReviewVO(); // vo 준비
        reviewVO.setRevNo(revNo); // revNo 지정
        reviewVO.setRevStar(request.getRevStar());
        reviewVO.setContent(request.getContent());
        // 리뷰 수정한 값을 vo에 저장

        reviewMapper.updateReview(reviewVO);// vo를 통해 mapper 호출해서 update 실행

        ReviewVO updatedReviewVO = reviewMapper.findReview(revNo);// 방금 만든 리뷰 다시 조회
        updatedReviewVO.setImages(reviewMapper.findReviewImages(updatedReviewVO.getRevNo()));

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

    /**
     * 특정 리뷰의 이미지 목록을 조회하는 서비스 메서드입니다. REVIEW_IMAGE 테이블을 조회하는 Mapper 메서드에 위임합니다.
     *
     * @param revNo 이미지 조회 대상 리뷰 번호
     * @return 해당 리뷰에 연결된 이미지 경로(또는 URL) 문자열 리스트
     */
    @Override
    public List<String> getReviewImages(int revNo) {
        // Mapper를 통해 DB에서 이미지 목록을 조회하고 URL만 추출하여 반환
        return reviewMapper.findReviewImages(revNo).stream()
            .sorted((a, b) -> a.getRiSort() - b.getRiSort())
            .map(img -> img.getRiUrl())
            .toList();
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
        dto.setContent(vo.getContent());
        dto.setLikeCount(vo.getLikeCount()); // vo 값들 가져다가 dto에 넣어주기
        dto.setRevTrustScore(vo.getRevTrustScore());
        dto.setRevTrustRank(vo.getRevTrustRank());
        dto.setPhotoCount(vo.getPhotoCount());

        // 이미지 리스트를 DTO에 매핑
        if (vo.getImages() != null) {
            dto.setImages(
                vo.getImages().stream()
                    .sorted((a, b) -> a.getRiSort() - b.getRiSort())
                    .map(img -> img.getRiUrl())
                    .toList()
            );
        }

        if (vo.getRevDate() != null) {
            dto.setRevDate(vo.getRevDate().toString()); // "yyyy-MM-dd" 형식 반환
        }

        return dto;
    }

}
