package com.rayan.demo.model.enums;

public enum NotificationType {

    EMAIL("EMAIL"),
    SMS("SMS");

    private String value;


    NotificationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}
