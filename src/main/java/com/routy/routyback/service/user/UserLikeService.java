package com.routy.routyback.service.user;

import com.routy.routyback.dto.user.UserLikeResponse;
import com.routy.routyback.mapper.user.UserLikeMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * IUserLikeService 의 실제 구현체입니다.
 * 좋아요 CRUD와 상태 체크 로직을 처리합니다.
 */
@Service
@RequiredArgsConstructor
public class UserLikeService implements IUserLikeService {

    private final UserLikeMapper userLikeMapper;

    @Override
    public List<UserLikeResponse> getUserLikeProducts(Long userNo, String type) {
        return userLikeMapper.selectUserLikeProducts(userNo, type);
    }

    @Override
    public int addLike(Long userNo, Long productId, String type) {
        return userLikeMapper.insertLike(userNo, productId, type);
    }

    @Override
    public int removeLike(Long userNo, Long productId, String type) {
        return userLikeMapper.deleteLike(userNo, productId, type);
    }

    @Override
    public boolean isLiked(Long userNo, Long productId, String type) {
        return userLikeMapper.countLike(userNo, productId, type) > 0;
    }
}