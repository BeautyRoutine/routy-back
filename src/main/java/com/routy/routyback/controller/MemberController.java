package com.routy.routyback.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.routy.routyback.service.MemberService;
import com.routy.routyback.dto.MemberDTO;
import com.routy.routyback.dto.MemberSearchRequest;

/**
 * 로그인 / 회원가입 컨트롤러
 * 회원 관련 API 기본 구조
 */

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 서버 연결 확인용
    // 실행 후 http://localhost:8080/member/health 접속해서 문자열 뜨면 정상입니다.
    @GetMapping("/health")
    public String healthCheck() {
        return "Routy Member API is running";
    }

    // 회원가입
    // 요청: JSON(email, password, nickname, skinType 등)
    // 응답: resultCode, resultMsg, resultTime, data
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody MemberDTO memberDTO) {
        return memberService.signUp(memberDTO);
    }

    // 로그인
    // 요청: JSON(email, password)
    // 응답: JWT 토큰 또는 에러 메시지
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberDTO memberDTO) {
        return memberService.login(memberDTO);
    }

    // 로그아웃
    // 토큰 만료 또는 세션 종료 처리
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        return memberService.logout(token);
    }

    // 회원 전체 목록 조회 (관리자용)
    // 요청: POST /member/all_list
    // 설명: 페이지네이션, 키워드 검색 가능
    @PostMapping("/all_list")
    public ResponseEntity<?> getAllMembers(@RequestBody MemberSearchRequest request) {
        return memberService.getAllMembers(request);
    }

    // 특정 회원 정보 조회
    // GET /member/info/{memId}
    @GetMapping("/info/{memId}")
    public ResponseEntity<?> getMemberInfo(@PathVariable String memId) {
        return memberService.getMemberInfo(memId);
    }
}
