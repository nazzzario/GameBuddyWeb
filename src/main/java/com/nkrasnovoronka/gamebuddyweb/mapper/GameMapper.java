package com.nkrasnovoronka.gamebuddyweb.mapper;

import com.nkrasnovoronka.gamebuddyweb.dto.game.RequestGame;
import com.nkrasnovoronka.gamebuddyweb.dto.game.ResponseGame;
import com.nkrasnovoronka.gamebuddyweb.model.Game;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GameMapper {

    @Mapping(target = "lobby", ignore = true)
    @Mapping(target = "genre", ignore = true)
    Game requestGameToEntity(RequestGame requestGame);

    @Mapping(target = "genre", source = "genre.genreName")
    ResponseGame entityToResponse(Game game);
}
