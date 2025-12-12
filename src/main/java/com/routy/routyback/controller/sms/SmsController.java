package com.routy.routyback.controller.sms;

import com.routy.routyback.dto.sms.SmsRequestDto;
import com.routy.routyback.dto.sms.SmsVerifyDto;
import com.routy.routyback.service.sms.SmsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/sms")
public class SmsController {

    private final SmsService smsService;

    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendVerificationCode(@RequestBody SmsRequestDto request) {
        try {
            smsService.sendVerificationCode(request.getPhoneNumber());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "인증번호가 발송되었습니다.");
            
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "SMS 발송에 실패했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyCode(@RequestBody SmsVerifyDto request) {
        try {
            boolean isValid = smsService.verifyCode(request.getPhoneNumber(), request.getCode());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", isValid);
            response.put("verified", isValid);
            response.put("message", isValid ? "인증이 완료되었습니다." : "인증번호가 일치하지 않습니다.");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("verified", false);
            error.put("message", "인증 확인 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
