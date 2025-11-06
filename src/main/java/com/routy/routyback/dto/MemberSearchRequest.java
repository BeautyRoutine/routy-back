package com.routy.routyback.dto;

import lombok.Data;

@Data
public class MemberSearchRequest {
    private int page = 1;
    private int limit = 10;
    // type: email | nick | name 이것도 수정사항 있으시면 고치겠습니다 ! 
    private String type;
    private String keyword;
}
