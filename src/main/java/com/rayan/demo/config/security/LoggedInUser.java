package com.rayan.demo.config.security;

import lombok.Data;

@Data
public class LoggedInUser {
    private String userName;
    private String customerId;
    private String publicKey;
    private String secretKey;
}
