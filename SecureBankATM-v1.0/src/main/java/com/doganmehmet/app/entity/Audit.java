package com.doganmehmet.app.entity;

import com.doganmehmet.app.enums.AuditType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "audits")
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "audit_id")
    private long auditId;
    private String username;
    @Enumerated(EnumType.STRING)
    @Column(name = "audit_type")
    private AuditType auditType;
    @Column(name = "action_date")
    private LocalDateTime actionDate;
    @Column(name = "ip_address")
    private String ipAddress;
    private String description;

    public Audit(String username, AuditType auditType, LocalDateTime actionDate, String ipAddress, String description)
    {
        this.username = username;
        this.auditType = auditType;
        this.actionDate = actionDate;
        this.ipAddress = ipAddress;
        this.description = description;
    }
}
