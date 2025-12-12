package com.routy.routyback.mapper.admin;

import java.util.List;
import java.util.Map;

/**
 * 관리자 회원 관리 Mapper 인터페이스
 * - MyBatis XML(IMembersAdmDAO.xml)과 연결됩니다.
 * - 회원 전체 조회 기능을 제공합니다.
 */
public interface IMembersAdmDAO {

    /**
     * 전체 회원 목록 조회
     *
     * @return List<Map<String, Object>> 회원 정보 리스트
     */
    List<Map<String, Object>> listAllMembers();
}
