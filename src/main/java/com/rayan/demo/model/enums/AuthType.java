package com.rayan.demo.model.enums;

public enum AuthType {
    USERNAME_PASSWORD("USERNAME_PASSWORD"),
    SMS_MULTI_FACTOR("SMS_MULTI_FACTOR");

    private String value;


    AuthType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
