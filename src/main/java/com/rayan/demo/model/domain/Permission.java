package com.rayan.demo.model.domain;

import com.rayan.demo.model.domain.audit.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "R_PERMISSION")
@Entity
public class Permission extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
