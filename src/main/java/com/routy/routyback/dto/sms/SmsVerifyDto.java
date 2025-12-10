package com.routy.routyback.dto.sms;

public class SmsVerifyDto {
    private String phoneNumber;
    private String code;

    public SmsVerifyDto() {
    }

    public SmsVerifyDto(String phoneNumber, String code) {
        this.phoneNumber = phoneNumber;
        this.code = code;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
