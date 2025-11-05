package com.routy.routyback.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 회원 목록 조회 요청 DTO입니다.
 * 페이지/키워드 기본만 두겠습니다.
 */
@Getter
@Setter
@ToString
public class MemberSearchRequest {

    private String type;       // 검색 조건
    private String keyword;    // 검색어
    private Integer page = 1;  // 페이지
    private Integer limit = 20;// 페이지 크기
}
