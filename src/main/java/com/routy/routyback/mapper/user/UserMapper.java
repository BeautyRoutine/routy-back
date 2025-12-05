package com.routy.routyback.mapper.user;

import com.routy.routyback.domain.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * User 관련 MyBatis Mapper 인터페이스
 */
@Mapper
public interface UserMapper {

    /**
     * 이메일로 회원 조회
     */
    User findByEmail(@Param("email") String email);

    /**
     * 회원 번호로 조회
     */
    User findByUserNo(@Param("userNo") Long userNo);

    // userId로 회원 번호 조회
    Long findUserNoByUserId(String userId);

    /**
     * userId로 회원 조회
     */
    User findByUserId(@Param("userId") String userId);

    /**
     * 이메일 중복 체크
     */
    boolean existsByEmail(@Param("email") String email);

    /**
     * 회원 저장 (회원가입)
     */
    int insertUser(User user);

    /**
     * 피부 타입 저장
     */
    void updateSkinType(@Param("userNo") Long userNo, @Param("skinType") Integer skinType);

    /**
     * 피부 타입 조회
     */
    Integer selectSkinType(@Param("userNo") Long userNo);
}