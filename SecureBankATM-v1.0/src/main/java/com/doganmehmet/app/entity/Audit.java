package com.doganmehmet.app.entity;

import com.doganmehmet.app.enums.Action;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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
    private Action action;
    @Column(name = "action_date")
    private LocalDateTime actionDate;
    @Column(name = "ip_address")
    private String ipAddress;
    private String description;

    public Audit(String username, Action action, LocalDateTime actionDate, String ipAddress, String description)
    {
        this.username = username;
        this.action = action;
        this.actionDate = actionDate;
        this.ipAddress = ipAddress;
        this.description = description;
    }
}
