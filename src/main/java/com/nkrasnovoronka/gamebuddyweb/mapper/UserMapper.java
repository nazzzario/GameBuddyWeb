package com.nkrasnovoronka.gamebuddyweb.mapper;

import com.nkrasnovoronka.gamebuddyweb.dto.user.RequestUser;
import com.nkrasnovoronka.gamebuddyweb.model.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User requestUserToEntity(RequestUser requestUser);
}

