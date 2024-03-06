package com.rayan.demo.model.dto.user;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class LoginResDto {
    private String token;
    private String refreshToken;
    private Set<String> authorities;
    private Long expireAt;

    @Override
    public String toString() {
        return "{" +
                ", authorities=" + authorities +
                ", expireAt=" + expireAt +
                '}';
    }
}
