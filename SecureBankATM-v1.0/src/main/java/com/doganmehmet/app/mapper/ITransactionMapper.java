package com.doganmehmet.app.mapper;

import com.doganmehmet.app.dto.TransactionDTO;
import com.doganmehmet.app.entity.Transaction;
import com.doganmehmet.app.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(implementationName = "TransactionMapperImpl", componentModel = "spring")
public interface ITransactionMapper {

    @Mapping(source = "fromUser", target = "fromUser")
    @Mapping(source = "toUser", target = "toUser")
    List<TransactionDTO> toListTransactionDTO(List<Transaction> transactions);

    default String map(User user)
    {
        return user != null ? user.getUsername() : null;
    }
}
