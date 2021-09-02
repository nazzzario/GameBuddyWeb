package com.nkrasnovoronka.gamebuddyweb.service.impl;

import com.nkrasnovoronka.gamebuddyweb.model.Lobby;
import com.nkrasnovoronka.gamebuddyweb.model.user.User;
import com.nkrasnovoronka.gamebuddyweb.repository.LobbyRepository;
import com.nkrasnovoronka.gamebuddyweb.service.LobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LobbyServiceImpl implements LobbyService {

    private final LobbyRepository lobbyRepository;

    @Autowired
    public LobbyServiceImpl(LobbyRepository lobbyRepository) {
        this.lobbyRepository = lobbyRepository;
    }

    @Override
    public Lobby create(Lobby lobby) {
        return lobbyRepository.save(lobby);
    }

    @Override
    public void update(Long id, Lobby updated) {
        Optional<Lobby> byId = lobbyRepository.findById(id);
        if (byId.isPresent()) {
            Lobby lobby = byId.get();
            lobby.setLobbyName(updated.getLobbyName());
            lobby.setAmountPlayer(updated.getAmountPlayer());
            lobby.setDescription(updated.getDescription());
            lobby.setGame(updated.getGame());
        }
    }

    @Override
    public void delete(Long id) {
        lobbyRepository.deleteById(id);
    }

    @Override
    public Lobby get(Long id) {
        return lobbyRepository.getById(id);
    }

    @Override
    public List<Lobby> getAll() {
        return lobbyRepository.findAll();
    }

    @Override
    public void addUserToLobby(Long lobbyId, User user) {
        Lobby byId = lobbyRepository.getById(lobbyId);
        byId.addUserToLobby(user);
    }

    @Override
    public List<Lobby> findAllLobbiesByGameName(String gameName) {
        return lobbyRepository.findAllByGameName(gameName);
    }
}
