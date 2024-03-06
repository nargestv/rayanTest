package com.rayan.demo.log;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SingleVariableBody {
    private String value;

    public static SingleVariableBody of(Object value) {
        return new SingleVariableBody(String.valueOf(value));
    }
}
