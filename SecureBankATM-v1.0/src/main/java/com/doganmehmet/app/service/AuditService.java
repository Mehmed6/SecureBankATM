package com.doganmehmet.app.service;

import com.doganmehmet.app.entity.Audit;
import com.doganmehmet.app.enums.Action;
import com.doganmehmet.app.repository.IAuditRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditService {
    private final IAuditRepository m_auditRepository;
    private final HttpServletRequest m_request;

    public AuditService(IAuditRepository auditRepository, HttpServletRequest request)
    {
        m_auditRepository = auditRepository;
        m_request = request;
    }

    private String getIpAddress()
    {
        var ip = m_request.getHeader("x-forwarded-for");

        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip))
            ip = m_request.getRemoteAddr();

        return ip;
    }

    public void logAudit(String username, Action action, String description)
    {
        var ip = getIpAddress();
        var actionDate = LocalDateTime.now();
        m_auditRepository.save(new Audit(username, action, actionDate, ip, description));
    }
}
