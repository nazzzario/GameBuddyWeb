package com.nkrasnovoronka.gamebuddyweb.service;

import com.nkrasnovoronka.gamebuddyweb.model.Lobby;
import com.nkrasnovoronka.gamebuddyweb.model.user.User;

public interface LobbyService extends CrudService<Lobby, Long>{

    void addUserToLobby(Long lobbyId, User user);
}
