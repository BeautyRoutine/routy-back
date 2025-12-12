package com.routy.routyback.dto.user;

/**
 * 서비스에서 사용할 Kakao 사용자 정보 DTO
 * - id, email, nickname 정도만 우선 사용합니다. 
 * - 필요하면 필드를 언제든지 추가할 수 있습니다. 
 */
public class KakaoUserProfile {

    // 카카오에서 내려주는 고유 사용자 id
    private Long id;

    // 카카오 계정 이메일 (동의받은 경우에만 옵니다. )
    private String email;

    // 프로필 닉네임
    private String nickname;

    // === getter / setter ===

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
