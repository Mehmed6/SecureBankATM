package com.doganmehmet.app.mapper;

import com.doganmehmet.app.dto.BankDTO;
import com.doganmehmet.app.entity.Bank;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(implementationName = "BankMapperImpl", componentModel = "spring")
public interface IBankMapper {

    BankDTO toBankDTO(Bank bank);

    default Page<BankDTO> toBankDTOPage(Page<Bank> banks)
    {
        return banks.map(this::toBankDTO);
    }
}
