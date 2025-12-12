package com.routy.routyback.service.admin;

import com.routy.routyback.mapper.admin.IMembersAdmDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 관리자 회원 관리 Service
 * - 회원 목록 조회 기능을 제공합니다.
 */
@Service
public class MemberAdmService {

    private final IMembersAdmDAO membersAdmDAO;

    public MemberAdmService(IMembersAdmDAO membersAdmDAO) {
        this.membersAdmDAO = membersAdmDAO;
    }

    /**
     * 전체 회원 목록 조회
     *
     * @return List<Map<String, Object>> 회원 리스트
     */
    public List<Map<String, Object>> listAllMembers() {
        return membersAdmDAO.listAllMembers();
    }
}
