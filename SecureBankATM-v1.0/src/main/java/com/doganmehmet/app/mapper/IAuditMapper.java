package com.doganmehmet.app.mapper;

import com.doganmehmet.app.dto.AuditDTO;
import com.doganmehmet.app.entity.Audit;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(implementationName = "AuditMapperImpl", componentModel = "spring")
public interface IAuditMapper {

    List<AuditDTO> toAuditDTOList(List<Audit> auditList);

    AuditDTO toAuditDTO(Audit audit);

    default Page<AuditDTO> toAuditDTOPage(Page<Audit> auditPage)
    {
        return auditPage.map(this::toAuditDTO);
    }
}
