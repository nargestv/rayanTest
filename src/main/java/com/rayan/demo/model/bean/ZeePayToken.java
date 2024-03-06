package com.rayan.demo.model.bean;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ZeePayToken {
    private static ZeePayToken zeePayToken = null;

    public String accessToken;
    private LocalDateTime expireAccessToken;
    public String tokenType;


    public static ZeePayToken getInstance() {
        if (zeePayToken == null)
            return new ZeePayToken();
        return zeePayToken;
    }
}
