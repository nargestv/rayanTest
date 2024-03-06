package com.rayan.demo.model.enums;

public enum GenderType {
    FEMALE("FEMALE"),
    MALE("MALE");

    private String value;


    GenderType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
