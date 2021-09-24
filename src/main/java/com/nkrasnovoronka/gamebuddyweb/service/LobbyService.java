package com.nkrasnovoronka.gamebuddyweb.service;

import com.nkrasnovoronka.gamebuddyweb.model.Lobby;
import com.nkrasnovoronka.gamebuddyweb.model.user.User;

import java.util.List;

public interface LobbyService extends CrudService<Lobby, Long> {

    void addUserToLobby(Long lobbyId, User user);

    List<Lobby> findAllLobbiesByGameName(String gameName);
}
