package com.routy.routyback.service;

import com.routy.routyback.dto.MemberDTO;
import com.routy.routyback.dto.MemberSearchRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 회원 관련 서비스 로직입니다.
 * DB 연동 전, 기본 구조만 두겠습니다. 
 */
@Service
public class MemberService {

    // 회원가입
    public ResponseEntity<?> signUp(MemberDTO memberDTO) {
        // TODO: 비밀번호 암호화, 중복체크, DB insert
        Map<String, Object> data = new HashMap<>();
        data.put("email", memberDTO.getEmail());
        data.put("nickname", memberDTO.getNickname());
        return success(data);
    }

    // 로그인
    public ResponseEntity<?> login(MemberDTO memberDTO) {
        // TODO: 자격 검증 후 JWT 발급
        Map<String, Object> data = new HashMap<>();
        data.put("token", "temporary-jwt-token");
        data.put("email", memberDTO.getEmail());
        return success(data);
    }

    // 로그아웃
    public ResponseEntity<?> logout(String token) {
        // TODO: 토큰 무효화/블랙리스트 처리
        Map<String, Object> data = new HashMap<>();
        data.put("logout", true);
        data.put("token", token);
        return success(data);
    }

    // 회원 전체 목록 조회 (관리자용)
    public ResponseEntity<?> getAllMembers(MemberSearchRequest request) {
        // TODO: DB 조회 + 페이지네이션
        Map<String, Object> page = new HashMap<>();
        page.put("page", request.getPage());
        page.put("limit", request.getLimit());
        page.put("list", List.of()); // 임시 빈 리스트
        return success(page);
    }

    // 특정 회원 상세 조회
    public ResponseEntity<?> getMemberInfo(String memId) {
        // TODO: DB 단건 조회
        Map<String, Object> data = new HashMap<>();
        data.put("memId", memId);
        data.put("email", "sample@routy.com");
        data.put("nickname", "샘플");
        return success(data);
    }

    // 공통 성공 응답 포맷
    private ResponseEntity<Map<String, Object>> success(Object body) {
        Map<String, Object> res = new HashMap<>();
        res.put("resultCode", 200);
        res.put("resultMsg", "SUCCESS");
        res.put("resultTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        res.put("data", body);
        return ResponseEntity.ok(res);
    }
}
