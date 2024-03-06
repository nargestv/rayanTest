package com.rayan.demo.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RefreshTokenReqDto {
    @NotEmpty
    private String refreshToken;
}
