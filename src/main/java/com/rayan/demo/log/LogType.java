package com.rayan.demo.log;

public enum LogType {
    REQUEST("REQUEST"), RESPONSE("RESPONSE"), ERROR("ERROR");

    private String value;

    LogType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
