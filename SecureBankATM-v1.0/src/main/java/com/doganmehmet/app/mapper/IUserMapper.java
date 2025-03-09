package com.doganmehmet.app.mapper;

import com.doganmehmet.app.dto.BankUsersDTO;
import com.doganmehmet.app.dto.UserDTO;
import com.doganmehmet.app.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(implementationName = "UserMapperImpl", componentModel = "spring")
public interface IUserMapper {

    User toUser(UserDTO userDTO);


    List<BankUsersDTO> toBankUsersDTOList(List<User> userList);
}
