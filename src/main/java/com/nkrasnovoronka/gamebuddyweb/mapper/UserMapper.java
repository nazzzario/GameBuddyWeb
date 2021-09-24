package com.nkrasnovoronka.gamebuddyweb.mapper;

import com.nkrasnovoronka.gamebuddyweb.dto.user.RequestUser;
import com.nkrasnovoronka.gamebuddyweb.dto.user.ResponseUser;
import com.nkrasnovoronka.gamebuddyweb.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "userStatus", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "createdLobbies", ignore = true)
    User requestUserToEntity(RequestUser requestUser);

    ResponseUser entityToResponseUser(User user);
}

