package com.rayan.demo.model.dto.user;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
public class LoginReqDto {
    private String email;
    private String username;
    private String password;
}
