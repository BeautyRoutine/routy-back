package com.routy.routyback.service.sms;

import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

@Service
public class SmsService {

    private final DefaultMessageService messageService;
    private final RedisTemplate<String, String> redisTemplate;

    @Value("${solapi.from-number}")
    private String fromNumber;

    private static final String SMS_PREFIX = "SMS:";
    private static final int CODE_LENGTH = 6;
    private static final int EXPIRE_TIME = 3;

    public SmsService(DefaultMessageService messageService, RedisTemplate<String, String> redisTemplate) {
        this.messageService = messageService;
        this.redisTemplate = redisTemplate;
    }

    public void sendVerificationCode(String phoneNumber) {
        if (!isValidPhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException("유효하지 않은 전화번호입니다.");
        }

        String code = generateVerificationCode();
        String redisKey = SMS_PREFIX + phoneNumber;
        redisTemplate.opsForValue().set(redisKey, code, EXPIRE_TIME, TimeUnit.MINUTES);

        Message message = new Message();
        message.setFrom(fromNumber);
        message.setTo(phoneNumber);
        message.setText("[Routy] 인증번호는 [" + code + "]입니다. 3분 이내에 입력해주세요.");

        try {
            SingleMessageSentResponse response = messageService.sendOne(new SingleMessageSendingRequest(message));
            System.out.println("SMS 발송 성공: " + response);
        } catch (Exception e) {
            System.err.println("SMS 발송 실패: " + e.getMessage());
            redisTemplate.delete(redisKey);
            throw new RuntimeException("SMS 발송에 실패했습니다.", e);
        }
    }

    public boolean verifyCode(String phoneNumber, String code) {
        String redisKey = SMS_PREFIX + phoneNumber;
        String savedCode = redisTemplate.opsForValue().get(redisKey);

        if (savedCode == null) {
            return false;
        }

        boolean isValid = savedCode.equals(code);

        if (isValid) {
            redisTemplate.delete(redisKey);
        }

        return isValid;
    }

    private String generateVerificationCode() {
        SecureRandom random = new SecureRandom();
        int code = random.nextInt(1000000);
        return String.format("%06d", code);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }
        String regex = "^01[0-9]-?[0-9]{3,4}-?[0-9]{4}$";
        return phoneNumber.matches(regex);
    }
}
