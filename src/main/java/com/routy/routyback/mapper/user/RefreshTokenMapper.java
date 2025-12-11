package com.routy.routyback.mapper.user;

import com.routy.routyback.domain.user.RefreshToken;
import org.apache.ibatis.annotations.*;

@Mapper
public interface RefreshTokenMapper {

    @Insert("""
        INSERT INTO REFRESH_TOKEN (user_id, refresh_token, expires_at)
        VALUES (#{userId}, #{refreshToken}, #{expiresAt})
    """)
    void insert(RefreshToken token);

    @Update("""
        UPDATE REFRESH_TOKEN
        SET refresh_token = #{refreshToken},
            expires_at = #{expiresAt}
        WHERE user_id = #{userId}
    """)
    void update(RefreshToken token);

    @Select("SELECT * FROM REFRESH_TOKEN WHERE user_id = #{userId}")
    RefreshToken findByUserId(String userId);

    @Delete("DELETE FROM REFRESH_TOKEN WHERE user_id = #{userId}")
    void delete(String userId);
}
