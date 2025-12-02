/**
 * 마이페이지 - 사용자 프로필 정보를 조회하기 위한 MyBatis Mapper 인터페이스입니다. USERS, REVIEW, POINT_HISTORY 테이블을 조회해서 한 번에 프로필 요약 정보를 가져오는 역할을 합니다.
 * @author 김지용
 */
package com.routy.routyback.mapper.user.mypage;

import com.routy.routyback.dto.user.mypage.UserProfileResponse;
import com.routy.routyback.dto.user.mypage.UserProfileUpdateRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper // MyBatis가 이 인터페이스를 Mapper로 인식하도록 해주는 애노테이션
public interface UserProfileMapper {

    /**
     * 특정 회원의 프로필 정보를 조회합니다.
     *
     * @param userNo 조회할 회원 번호 (USERS.USERNO)
     * @return UserProfileResponseDto 프로필 요약 정보
     *         - userNo      : 회원 번호
     *         - userName    : 회원 이름
     *         - userLevel   : 회원 등급
     *         - reviewCount : 작성한 리뷰 개수
     *         - points      : 누적 포인트 합계
     */
    UserProfileResponse selectUserProfile(Long userNo);

    /**
     * 사용자 프로필 정보를 수정합니다.
     * @param userNo 수정할 회원 번호
     * @param req 수정 요청 데이터
     * @return int 업데이트된 행 수
     */
    int updateUserProfile(@Param("userNo") Long userNo,
        @Param("req") UserProfileUpdateRequest req);
}