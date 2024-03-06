package com.rayan.demo.model.enums;

public enum RoleConstant {

    OPERATOR("OPERATOR"),
    LIRA_REGISTER("LIRA_REGISTER"),
    LIRA_USER("LIRA_USER");

    private String value;

    public String getValue() {
        return value;
    }

    RoleConstant(String value) {
        this.value = value;
    }
}
