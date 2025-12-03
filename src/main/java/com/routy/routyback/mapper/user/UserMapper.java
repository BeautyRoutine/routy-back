package com.routy.routyback.mapper.user;

import com.routy.routyback.domain.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface UserMapper {
    User findByEmail(@Param("email") String email);
    User findByUserNo(@Param("userNo") Long userNo);
    User findByUserId(@Param("userId") String userId);
    boolean existsByEmail(@Param("email") String email);
    int insertUser(User user);
    int updateSkinProfile(Map<String, Object> params);
    Map<String, Object> getSkinProfile(@Param("userId") String userId);
}