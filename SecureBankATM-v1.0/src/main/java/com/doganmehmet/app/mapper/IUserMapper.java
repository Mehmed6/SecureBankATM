package com.doganmehmet.app.mapper;

import com.doganmehmet.app.dto.UserDTO;
import com.doganmehmet.app.entity.User;
import org.mapstruct.Mapper;

@Mapper(implementationName = "UserMapperImpl", componentModel = "spring")
public interface IUserMapper {


    User toUser(UserDTO userDTO);
}
