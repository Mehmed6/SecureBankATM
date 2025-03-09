package com.doganmehmet.app.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AuditDTO {

    private String username;
    private String auditType;
    private LocalDateTime actionDate;
    private String ipAddress;
    private String description;
}
