package com.routy.routyback.service.user;

import com.routy.routyback.domain.ReviewImageVO;
import com.routy.routyback.domain.ReviewVO;
import com.routy.routyback.dto.review.ReviewCreateRequest;
import com.routy.routyback.dto.review.ReviewLikeResponse;
import com.routy.routyback.dto.review.ReviewListResponse;
import com.routy.routyback.dto.review.ReviewListResponse.PaginationDto;
import com.routy.routyback.dto.review.ReviewListResponse.SummaryDto;
import com.routy.routyback.dto.review.ReviewResponse;
import com.routy.routyback.dto.review.ReviewUpdateRequest;
import com.routy.routyback.mapper.user.ReviewMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service("reviewService")
@Transactional
public class ReviewService implements IReviewService {

    @Autowired
    ReviewMapper reviewMapper;

    @Autowired
    ReviewTrustCalculator reviewTrustCalculator;
    
    @Value("${file.upload-dir}")
    private String uploadDir;

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
            List<String> feedbacks = reviewMapper.findReviewFeedback(vo.getRevNo()); //반응 받아오기
            dto.setFeedback(feedbacks);
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
    public ReviewResponse createReview(int prdNo, ReviewCreateRequest request, List<MultipartFile> files) {
    	
    	//리뷰 중복 방지
   	 	int count = reviewMapper.checkReviewExists(request.getUserNo(), prdNo);
   	 		if (count > 0) {
   	 			// IllegalArgumentException을 던지면 ApiResponse.fromException에서 400 에러로 처리됨
   	 			throw new IllegalArgumentException("이미 이 상품에 대한 리뷰를 작성하셨습니다.");
   	 		}
    	// odNo가 0이면 null로 변경
    	if (request.getOdNo() != null && request.getOdNo() == 0) {
            request.setOdNo(null);
        }
    	
    	
    	
        // 1) VO 생성 및 기본값 세팅
        ReviewVO reviewVO = new ReviewVO();
        reviewVO.setPrdNo(prdNo);
        reviewVO.setUserNo(request.getUserNo());
        reviewVO.setRevRank(1); 
        reviewVO.setRevStar(request.getRevStar());
        reviewVO.setContent(request.getContent());
        reviewVO.setOdNo(request.getOdNo());
        reviewVO.setUserSkin(request.getUserSkin());
        reviewVO.setUserColor(request.getUserColor());

        // 2) 리뷰 저장 → REVNO 생성됨
        reviewMapper.insertReview(reviewVO);

        // 2-1)리뷰 이미지 리스트 추가
        if (files != null && !files.isEmpty()) {
            int sort = 1;  //정렬 1부터
            for (MultipartFile file : files) {
                // saveFile 메서드 호출로 "images/review/난수.jpg" 반환
                String dbPath = saveFile(file); 
                
                // 경로가 잘 나왔으면 DB에 저장
                if (dbPath != null) {
                    ReviewImageVO imageVO = new ReviewImageVO();
                    imageVO.setRevNo(reviewVO.getRevNo());
                    imageVO.setRiUrl(dbPath); // 받아온 경로 그대로
                    imageVO.setRiSort(sort++);
                    reviewMapper.insertReviewImage(imageVO);
                }
            }
        }
        
        // 2-2) 피드백 추가
        List<Integer> codes = request.getFeedback(); // 프론트에서 보낸 [101, 501 등]
        if (codes != null && !codes.isEmpty()) {
            for (Integer code : codes) {
                // 반복문 돌면서 DB에 하나씩 꽂아줍니다.
                reviewMapper.insertReviewFeedback(reviewVO.getRevNo(), code);
            }
        }
        
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
        ReviewResponse response =convertVoToResponseDto(created);
        response.setFeedback(reviewMapper.findReviewFeedback(created.getRevNo()));
         
         return response;
    }

    @Override
    public ReviewResponse updateReview(int revNo, ReviewUpdateRequest request, List<MultipartFile> newFiles) {
        ReviewVO reviewVO = new ReviewVO(); // vo 준비
        reviewVO.setRevNo(revNo); // revNo 지정
        reviewVO.setRevStar(request.getRevStar());
        reviewVO.setContent(request.getContent());
        // 리뷰 수정한 값을 vo에 저장

        reviewMapper.updateReview(reviewVO);// vo를 통해 mapper 호출해서 update 실행
        
        //이미지리스트가 null이 아니거나, 새 파일이 오면, 즉 수정사항이 있을때만 프론트에서 revIamges를 보냄
        boolean isImageChanged = (request.getRevImages() != null) || (newFiles != null && !newFiles.isEmpty());

        if (isImageChanged) {
            // 이미지 변경이 감지되었을 때 기존 매핑 삭제
            reviewMapper.deleteReviewImages(revNo);

            int sort = 1; //정렬은 1부터

            //기존 이미지 다시 넣기
            if (request.getRevImages() != null) {
                for (String oldUrl : request.getRevImages()) {
                    ReviewImageVO imageVO = new ReviewImageVO();
                    imageVO.setRevNo(revNo);
                    imageVO.setRiUrl(oldUrl);
                    imageVO.setRiSort(sort++);
                    reviewMapper.insertReviewImage(imageVO);
                }
            }

            // 새 파일 추가하기
            if (newFiles != null) {
                for (MultipartFile file : newFiles) {
                    String newPath = saveFile(file); // 파일 저장
                    if (newPath != null) {
                        ReviewImageVO imageVO = new ReviewImageVO();
                        imageVO.setRevNo(revNo);
                        imageVO.setRiUrl(newPath);
                        imageVO.setRiSort(sort++);
                        reviewMapper.insertReviewImage(imageVO);
                    }
                }
            }
        }

        //피드백 수정
        List<Integer> newFeedbacks = request.getFeedback(); // [101, 501]

        // 피드백 리스트가 null이 아니면 (빈 배열 [] 이라도 왔으면 수정으로 간주)
        if (newFeedbacks != null) {
            
            //기존 태그 싹 삭제 (Mapper에 만들어둔 거)
            reviewMapper.deleteReviewFeedback(revNo);

            // 새 태그 등록
            // 빈 배열이면 삭제만 되고 끝남 (태그 다 지운 경우)
            if (!newFeedbacks.isEmpty()) {
                for (Integer code : newFeedbacks) {
                    // Mapper에 만들어둔 insert (revNo, code)
                    reviewMapper.insertReviewFeedback(revNo, code);
                }
            }
        }
        
        
            //신뢰도 점수 재계산 (텍스트 길이 & 사진 개수 반영)
            // DB에서 최신 상태의 리뷰 정보를 다시 조회
            
            // 이때 SQL로  photoCount,content 체크
            ReviewVO updatedVO = reviewMapper.findReview(revNo);
            updatedVO.setImages(reviewMapper.findReviewImages(revNo)); // 이미지 리스트 세팅

            // 리뷰 신뢰도 재계산
            double score = reviewTrustCalculator.calculateTrustScore(updatedVO);
            
            //등급 산정
            boolean isVerified = updatedVO.getOdNo() != null; // 구매 여부 판단
            String rank = reviewTrustCalculator.calculateTrustRank(score, isVerified);

            updatedVO.setRevTrustScore(score);
            updatedVO.setRevTrustRank(rank);
            reviewMapper.updateReviewTrustScore(updatedVO);

            // 4. 최종 결과 반환
            ReviewResponse response =convertVoToResponseDto(updatedVO);
            response.setFeedback(reviewMapper.findReviewFeedback(revNo));
             
             return response;
        
    }
    

    @Override
    public void deleteReview(int revNo) {
    	reviewMapper.deleteReviewImages(revNo); //이미지 삭제
    	reviewMapper.deleteReviewUpdown(revNo); //좋아요 삭제
    	reviewMapper.deleteReviewFeedback(revNo); //피드백 삭제
    	
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
        dto.setUserSkin(vo.getUserSkin());
        dto.setUserColor(vo.getUserColor());

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

    private String saveFile(MultipartFile file) {
        if (file.isEmpty()) return null;

        // 폴더 확인 및 생성
        // 설정 파일에서 읽어온 C:/BeautyRoutine/.../public/images/review/ 경로 사용중
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs(); // 폴더가 없으면 생성
        }

        //파일명 중복 방지 (UUID)
        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); //확장자 빼내기
        String savedFileName = UUID.randomUUID().toString() + extension; 

        //실제 저장 (백엔드가 프론트 폴더로 파일 전송)
        try {
            // C:/BeautyRoutine/.../public/images/review/난수.jpg
            file.transferTo(new File(uploadDir + savedFileName));
        } catch (IOException e) {
            throw new RuntimeException("이미지 저장 실패: 경로를 확인하세요 -> " + uploadDir, e);
        }

        // DB에 저장할 '파일명' 반환
        return "/images/review/" + savedFileName;
    }

    
    
    /**
     * 전체 리뷰 신뢰도 재계산
     * 리뷰 1000개 넘어서 100개씩 나눠서 업데이트
     */
	@Override
	public ReviewResponse totalRecalculation() {
		long startTime = System.currentTimeMillis();
        
        try {
            // 1️. 전체 리뷰 개수 조회
            int totalCount = reviewMapper.countAllReviews();
            if (totalCount == 0) {
                System.out.println("⚠️ 재계산할 리뷰가 없습니다.");
                ReviewResponse response = new ReviewResponse();
                response.setRevNo(0);
                response.setContent("재계산할 리뷰가 없습니다.");
                return response;
            }
            
            System.out.println("========================================");
            System.out.println("🔄 신뢰도 전체 재계산 시작");
            System.out.println("📊 총 리뷰 수: " + totalCount);
            System.out.println("========================================");
            
            // 2️. 리뷰 분할
            int batchSize = 100; // 한 번에 100개씩 처리
            int successCount = 0;
            int processedCount = 0;
            
            // 3️⃣ 배치 단위로 처리
            for (int offset = 0; offset < totalCount; offset += batchSize) {
                Map<String, Object> params = new HashMap<>();
                params.put("prdNo", 0);     // 0 = 전체 상품
                params.put("offset", offset);
                params.put("limit", batchSize);
                
                List<ReviewVO> batch = reviewMapper.findReviews(params);
                
                // 배치 내 각 리뷰 처리
                for (ReviewVO vo : batch) {
                    processedCount++;
                    
                    try {
                        // 리뷰 이미지 목록 로드 (신뢰도 계산에 필요)
                        vo.setImages(reviewMapper.findReviewImages(vo.getRevNo()));
                        
                        // 신뢰도 점수 계산
                        double score = reviewTrustCalculator.calculateTrustScore(vo);
                        
                        // 신뢰도 등급 계산 (구매 인증 여부 확인)
                        boolean isVerified = vo.getOdNo() != null;
                        String rank = reviewTrustCalculator.calculateTrustRank(score, isVerified);
                        
                        // VO에 계산된 값 적용
                        vo.setRevTrustScore(score);
                        vo.setRevTrustRank(rank);
                        
                        // DB 업데이트
                        reviewMapper.updateReviewTrustScore(vo);
                        
                        successCount++;
                        
                    } catch (Exception e) {
                        // ❌ 실패한 리뷰 ID 즉시 출력
                        System.out.println("❌ 리뷰 재계산 실패 - revNo: " + vo.getRevNo() 
                            + " | 오류: " + e.getMessage());
                    }
                }
                
                // 진행 상황 출력 (100개마다)
                int percentage = (int) ((processedCount / (double) totalCount) * 100);
                System.out.println("⏳ 진행 중... " + processedCount + "/" + totalCount 
                    + " (" + percentage + "%)");
            }
            
            // 4️⃣ 처리 시간 계산
            long endTime = System.currentTimeMillis();
            long duration = (endTime - startTime) / 1000; // 초 단위
            
            // 5️⃣ 최종 결과 출력
            int failCount = totalCount - successCount;
            
            System.out.println("========================================");
            System.out.println("✅ 신뢰도 전체 재계산 완료");
            System.out.println("========================================");
            System.out.println("📊 총 리뷰 수: " + totalCount);
            System.out.println("✅ 성공: " + successCount);
            System.out.println("❌ 실패: " + failCount);
            System.out.println("⏱️ 소요 시간: " + duration + "초");
            System.out.println("========================================");
            
            // 6️⃣ 결과 반환
            ReviewResponse response = new ReviewResponse();
            response.setRevNo(0);
            response.setContent(String.format(
                "전체 재계산 완료 - 총: %d, 성공: %d, 실패: %d, 소요시간: %d초",
                totalCount, successCount, failCount, duration
            ));
            
            return response;
            
        } catch (Exception e) {
            System.err.println("========================================");
            System.err.println("🚨 전체 재계산 중 치명적 오류 발생");
            System.err.println("========================================");
            System.err.println("오류 메시지: " + e.getMessage());
            System.err.println("========================================");
            e.printStackTrace();
            throw new RuntimeException("신뢰도 전체 재계산에 실패했습니다.", e);
        }
	}
}
