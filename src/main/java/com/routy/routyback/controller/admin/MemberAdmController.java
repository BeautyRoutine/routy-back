package com.routy.routyback.controller.admin;

import com.routy.routyback.service.admin.MemberAdmService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 관리자 회원 관리 Controller
 * - 회원 목록 조회 API
 */
@RestController
@RequestMapping("/api/admin/members")

public class MemberAdmController {

    private final MemberAdmService memberAdmService;

    public MemberAdmController(MemberAdmService memberAdmService) {
        this.memberAdmService = memberAdmService;
    }

    /**
     * 전체 회원 목록 조회
     *
     * @return 회원 정보 리스트
     */
    @GetMapping("/list")
    public List<Map<String, Object>> list() {
        return memberAdmService.listAllMembers();
    }
}
