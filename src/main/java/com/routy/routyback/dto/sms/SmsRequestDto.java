package com.routy.routyback.dto.sms;

public class SmsRequestDto {
    private String phoneNumber;

    public SmsRequestDto() {
    }

    public SmsRequestDto(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

