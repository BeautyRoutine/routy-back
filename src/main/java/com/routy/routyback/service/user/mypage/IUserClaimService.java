package com.routy.routyback.service.user.mypage;

import com.routy.routyback.dto.user.mypage.UserClaimRequest;
import com.routy.routyback.dto.user.mypage.UserClaimResponse;

import java.util.List;

/**
 * 클레임 서비스 인터페이스
 * - 비즈니스 로직 정의
 */
public interface IUserClaimService {

    /**
     * 클레임 신청 처리
     * @param userNo 사용자 번호
     * @param request 신청 정보
     */
    void createClaim(Long userNo, UserClaimRequest request);

    /**
     * 사용자의 클레임 목록 조회
     * @param userNo 사용자 번호
     * @return 클레임 리스트
     */
    List<UserClaimResponse> getClaims(Long userNo);
}