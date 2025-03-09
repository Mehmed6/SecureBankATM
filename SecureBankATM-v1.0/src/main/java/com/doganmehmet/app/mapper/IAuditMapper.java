package com.doganmehmet.app.mapper;

import com.doganmehmet.app.dto.AuditDTO;
import com.doganmehmet.app.entity.Audit;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(implementationName = "AuditMapperImpl", componentModel = "spring")
public interface IAuditMapper {

    List<AuditDTO> toAuditDTOList(List<Audit> auditList);
}
