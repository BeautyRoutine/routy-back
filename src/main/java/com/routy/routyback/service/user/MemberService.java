package com.routy.routyback.service.user;

import com.routy.routyback.dto.MemberDTO;
import com.routy.routyback.dto.MemberSearchRequest;
import com.routy.routyback.mapper.user.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 회원 관련 서비스 로직 (USERS 테이블 스키마 기준) -오류뜨면 알려주세요 ! 
 */
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;

    /** 회원가입 */
    public ResponseEntity<?> signUp(MemberDTO memberDTO) {
        // TODO: 이메일 중복, 비밀번호 해시
        memberMapper.insertMember(memberDTO);

        Map<String, Object> data = new HashMap<>();
        data.put("email", memberDTO.getUserEmail());  // ← 여기!
        data.put("nickname", memberDTO.getUserNick()); // ← 여기!
        return success(data);
    }

    /** 로그인 */
    public ResponseEntity<?> login(MemberDTO memberDTO) {
        // TODO: 비밀번호 검증(BCrypt)
        MemberDTO found = memberMapper.findByEmail(memberDTO.getUserEmail()); // ← 여기!
        if (found == null) {
            return error(401, "UNAUTHORIZED");
        }
        Map<String, Object> data = new HashMap<>();
        data.put("token", "temporary-jwt-token");
        data.put("email", found.getUserEmail());   // ← 여기!
        data.put("nickname", found.getUserNick()); // ← 여기!
        return success(data);
    }

    /** 로그아웃 */
    public ResponseEntity<?> logout(String token) {
        Map<String, Object> data = new HashMap<>();
        data.put("logout", true);
        data.put("token", token);
        return success(data);
    }

    /** 회원 전체 목록(관리자) */
    public ResponseEntity<?> getAllMembers(MemberSearchRequest request) {
        int page = Math.max(request.getPage(), 1);
        int limit = Math.max(request.getLimit(), 1);
        int offset = (page - 1) * limit;

        List<MemberDTO> list = memberMapper.findAll(offset, limit, request.getType(), request.getKeyword());
        int totalCount = memberMapper.countAll(request.getType(), request.getKeyword());
        int totalPages = (int) Math.ceil((double) totalCount / limit);

        Map<String, Object> pagination = new HashMap<>();
        pagination.put("page", page);
        pagination.put("limit", limit);
        pagination.put("totalCount", totalCount);
        pagination.put("totalPages", totalPages);

        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        data.put("pagination", pagination);

        return success(data);
    }

    /** 특정 회원 상세 */
    public ResponseEntity<?> getMemberInfo(String memId) {
        Long id = Long.parseLong(memId);
        MemberDTO member = memberMapper.findById(id);
        if (member == null) return error(404, "NOT_FOUND");
        return success(member);
    }

    // 공통 응답입니다 ! 
    private ResponseEntity<Map<String, Object>> success(Object body) {
        Map<String, Object> res = new HashMap<>();
        res.put("resultCode", 200);
        res.put("resultMsg", "SUCCESS");
        res.put("resultTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        res.put("data", body);
        return ResponseEntity.ok(res);
    }

    private ResponseEntity<Map<String, Object>> error(int code, String msg) {
        Map<String, Object> res = new HashMap<>();
        res.put("resultCode", code);
        res.put("resultMsg", msg);
        res.put("resultTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return ResponseEntity.status(code).body(res);
    }
}
