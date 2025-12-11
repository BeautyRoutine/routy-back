package com.routy.routyback.mapper.user;

import com.routy.routyback.domain.auth.RefreshToken;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RefreshTokenMapper {
    void saveRefreshToken(RefreshToken ref);
    RefreshToken findByUserId(String userId);
}
