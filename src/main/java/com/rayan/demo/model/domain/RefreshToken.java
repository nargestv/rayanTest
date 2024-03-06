package com.rayan.demo.model.domain;

import com.rayan.demo.model.domain.audit.AbstractAuditingEntity;
import com.rayan.demo.utils.EncryptDecryptUtil;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Table(name = "R_REFRESH_TOKEN")
@Entity
public class RefreshToken extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String refreshToken;

    private LocalDateTime expiredAt;

    @OneToOne
    private User user;

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = EncryptDecryptUtil.encrypt(refreshToken);
    }

    public String getRefreshToken() {
        return EncryptDecryptUtil.decrypt(this.refreshToken);
    }
}
