package com.rayan.demo.model.domain;

import com.rayan.demo.model.domain.audit.AbstractAuditingEntity;
import com.rayan.demo.utils.EncryptDecryptUtil;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Data
@Table(name = "R_USER")
@Entity
public class User extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    private String email;

    private Boolean enabled;

    @ManyToMany
    @ToString.Exclude
    private Set<Role> roles;

    public void setPassword(String password) {
        this.password = EncryptDecryptUtil.encrypt(password);
    }

    public String getPassword() {
        return EncryptDecryptUtil.decrypt(password);
    }
}
