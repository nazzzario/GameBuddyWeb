package com.nkrasnovoronka.gamebuddyweb.mapper;

import com.nkrasnovoronka.gamebuddyweb.dto.lobby.RequestLobby;
import com.nkrasnovoronka.gamebuddyweb.dto.lobby.ResponseLobby;
import com.nkrasnovoronka.gamebuddyweb.model.Lobby;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LobbyMapper {

    @Mapping(target = "game", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "joinedUsers", ignore = true)
    Lobby requestLobbyToEntity(RequestLobby requestLobby);

//    @Mapping(target = "ownerName", source = "owner.login")
//    @Mapping(target = "gameName", source = "game.name")
//    @Mapping(target = "gameGenre", source = "game.genre.genreName")
    ResponseLobby entityToResponseLobby(Lobby lobby);

}
