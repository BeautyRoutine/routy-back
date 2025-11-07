package com.routy.routyback.mapper.user;

import com.routy.routyback.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {

    // 회원가입
    int insertMember(MemberDTO member);

    // 로그인 / 이메일 중복 체크 입니다.
    MemberDTO findByEmail(@Param("email") String email);

    // 단건 조회 입니다.
    MemberDTO findById(@Param("id") Long id);

    // 목록 + 검색 + 페이지네이션
    List<MemberDTO> findAll(@Param("offset") int offset,
                            @Param("limit") int limit,
                            @Param("type") String type,
                            @Param("keyword") String keyword);

    // 총 개수
    int countAll(@Param("type") String type,
                 @Param("keyword") String keyword);
}
