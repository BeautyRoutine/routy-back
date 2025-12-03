package com.routy.routyback.service.user.mypage;

import com.routy.routyback.dto.user.mypage.UserClaimRequest;
import com.routy.routyback.dto.user.mypage.UserClaimResponse;
import com.routy.routyback.mapper.user.UserMapper;
import com.routy.routyback.mapper.user.mypage.UserClaimMapper;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 클레임 서비스 구현체
 */
@Service
@RequiredArgsConstructor
public class UserClaimService implements IUserClaimService {

    private final UserClaimMapper claimMapper;
    private final UserMapper userMapper;

    @Override
    public void createClaim(String userId, UserClaimRequest request) {
        Long userNo = userMapper.findUserNoByUserId(userId);
        if (userNo == null) {
            return;
        }
        claimMapper.insertClaim(
            userNo,
            request.getOrderId(),
            request.getType(),
            request.getReason()
        );
    }

    @Override
    public List<UserClaimResponse> getClaims(String userId) {
        Long userNo = userMapper.findUserNoByUserId(userId);
        if (userNo == null) {
            return Collections.emptyList();
        }
        return claimMapper.selectClaims(userNo);
    }
}