package com.routy.routyback.mapper.user;

import com.routy.routyback.domain.auth.RefreshToken;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RefreshTokenMapper {

    void saveRefreshToken(RefreshToken token);

    RefreshToken getRefreshToken(String userId);

    void deleteRefreshToken(String userId);
}
