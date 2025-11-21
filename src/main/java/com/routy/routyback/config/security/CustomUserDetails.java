package com.routy.routyback.config.security;

import com.routy.routyback.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * 우리 시스템의 User 도메인을
 * 스프링 시큐리티에서 사용하는 UserDetails 로 감싸는 래퍼 클래스
 */
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;

    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // USERAUTH 컬럼에 권한 문자열이 들어간다고 가정 (ex. ROLE_USER)
        String role = user.getUserAuth() != null ? user.getUserAuth() : "ROLE_USER";
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return user.getUserPw();
    }

    @Override
    public String getUsername() {
        // 로그인은 USERID 기준
        return user.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 상세 정책 없으니 항상 true
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 잠금 로직 없으니 true
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 비밀번호 만료 정책 없음
    }

    @Override
    public boolean isEnabled() {
        // USERSTATUS = 1 이면 활성 계정이라고 가정
        return user.getUserStatus() == null || user.getUserStatus() == 1;
    }
}
