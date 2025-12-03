package com.routy.routyback.service.user.mypage;

import com.routy.routyback.dto.user.mypage.UserLikeResponse;
import com.routy.routyback.mapper.user.mypage.UserLikeMapper;
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
    public List<UserLikeResponse> getUserLikeProducts(String userId, String type) {
        return userLikeMapper.selectUserLikeProducts(userId, type);
    }

    @Override
    public int addLike(String userId, Long productId, String type) {
        return userLikeMapper.insertLike(userId, productId, type);
    }

    @Override
    public int removeLike(String userId, Long productId, String type) {
        return userLikeMapper.deleteLike(userId, productId, type);
    }

    @Override
    public boolean isLiked(String userId, Long productId, String type) {
        return userLikeMapper.countLike(userId, productId, type) > 0;
    }
}