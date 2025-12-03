package com.routy.routyback.mapper.user.mypage;

import org.apache.ibatis.annotations.Mapper;
import com.routy.routyback.dto.user.mypage.UserClaimResponse;
import java.util.List;

/**
 * 클레임 관련 Mapper
 * - DB 접근만 담당
 */
@Mapper
public interface UserClaimMapper {

    /**
     * 클레임 등록
     * @param userNo 사용자 번호
     * @param orderId 주문 번호
     * @param type CANCEL / REFUND / EXCHANGE
     * @param reason 신청 사유
     * @return insert 개수
     */
    int insertClaim(Long userNo, Long orderId, String type, String reason);

    /**
     * 사용자의 클레임 목록 조회
     * @param userNo 사용자 번호
     * @return 조회된 클레임 리스트
     */
    List<UserClaimResponse> selectClaims(Long userNo);
}