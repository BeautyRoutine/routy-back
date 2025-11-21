package com.routy.routyback.mapper.user;

import com.routy.routyback.domain.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * USERS 테이블 접근용 MyBatis 매퍼
 */
@Mapper
public interface UserMapper {

    // 기본 로그인: USERID 로 조회
    User findByUserId(@Param("userId") String userId);

    // 카카오 로그인: 이메일 기준으로 조회
    User findByUserEmail(@Param("userEmail") String userEmail);

    // 신규 회원 저장(로컬/카카오 공통)
    int insertUser(User user);
}
