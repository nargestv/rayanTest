package com.rayan.demo.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
public class RefreshTokenResDto {
    @ToString.Exclude
    private String token;
    @ToString.Exclude
    private String refreshToken;
    private Long expireAt;
}
