package com.routy.routyback.service.user.mypage;

import com.routy.routyback.dto.user.mypage.UserClaimRequest;
import com.routy.routyback.dto.user.mypage.UserClaimResponse;
import com.routy.routyback.mapper.user.mypage.UserClaimMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 클레임 서비스 구현체
 */
@Service
@RequiredArgsConstructor
public class UserClaimService implements IUserClaimService {

    private final UserClaimMapper claimMapper;

    @Override
    public void createClaim(Long userNo, UserClaimRequest request) {
        claimMapper.insertClaim(
            userNo,
            request.getOrderId(),
            request.getType(),
            request.getReason()
        );
    }

    @Override
    public List<UserClaimResponse> getClaims(Long userNo) {
        return claimMapper.selectClaims(userNo);
    }
}