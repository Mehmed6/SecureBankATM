package com.doganmehmet.app.mapper;

import com.doganmehmet.app.dto.BankUsersDTO;
import com.doganmehmet.app.dto.UserDTO;
import com.doganmehmet.app.entity.Bank;
import com.doganmehmet.app.entity.User;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

@Mapper(implementationName = "UserMapperImpl", componentModel = "spring")
public interface IUserMapper {

    User toUser(UserDTO userDTO);

    List<BankUsersDTO> toBankUsersDTOList(List<User> userList);

    BankUsersDTO toBankUsersDTO(User user);

    default Page<BankUsersDTO> toBankUsersDTOPage(Page<User> userList)
    {
        return userList.map(this::toBankUsersDTO);
    }
}
