package com.routy.routyback.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.routy.routyback.service.user.MemberService;
import com.routy.routyback.dto.MemberDTO;
import com.routy.routyback.dto.MemberSearchRequest;

/**
 * 회원 관련 API 컨트롤러
 * - 회원가입 / 로그인 / 로그아웃
 * - 회원 목록 조회, 단일 회원 조회
 */
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 서버 연결 확인용
    // http://localhost:8080/member/health 로 확인
    @GetMapping("/health")
    public String healthCheck() {
        return "Routy Member API is running";
    }

    // 회원가입
    // POST /member/signup
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody MemberDTO memberDTO) {
        return memberService.signUp(memberDTO);
    }

    // 로그인
    // POST /member/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberDTO memberDTO) {
        return memberService.login(memberDTO);
    }

    // 로그아웃
    // POST /member/logout
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        return memberService.logout(token);
    }

    // 회원 전체 목록 조회 (관리자용)
    // POST /member/all_list
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
