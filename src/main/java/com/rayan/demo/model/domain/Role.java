package com.rayan.demo.model.domain;

import com.rayan.demo.model.domain.audit.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Table(name = "R_ROLE")
@Entity
public class Role extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    private List<Permission> permissions;
}
