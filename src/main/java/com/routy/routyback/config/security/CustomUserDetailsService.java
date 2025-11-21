package com.routy.routyback.config.security;

import com.routy.routyback.domain.user.User;
import com.routy.routyback.mapper.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 스프링 시큐리티에서 username(저희 기준 USERID) 으로
 * 사용자 정보를 조회하는 서비스
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userMapper.findByUserId(username);

        if (user == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다. userId=" + username);
        }

        return new CustomUserDetails(user);
    }
}
