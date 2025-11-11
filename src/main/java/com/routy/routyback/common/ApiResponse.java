package com.routy.routyback.common;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class ApiResponse<T> {

    private final int resultCode;
    private final String resultMsg;
    private final String resultTime;
    private final T data;

    @Builder
    public ApiResponse(int resultCode, String resultMsg, T data) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        this.resultTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data){
        return ApiResponse.<T>builder()
                .resultCode(200)
                .resultMsg("SUCCESS")
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> error(int resultCode, String resultMsg){
        return ApiResponse.<T>builder()
                .resultCode(resultCode)
                .resultMsg(resultMsg)
                .data(null)
                .build();
    }
}
