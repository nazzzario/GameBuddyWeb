package com.nkrasnovoronka.gamebuddyweb.service;

import com.nkrasnovoronka.gamebuddyweb.model.Game;
import com.nkrasnovoronka.gamebuddyweb.model.Lobby;
import com.nkrasnovoronka.gamebuddyweb.model.user.User;
import com.nkrasnovoronka.gamebuddyweb.repository.LobbyRepository;
import com.nkrasnovoronka.gamebuddyweb.service.impl.LobbyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"h2db", "debug"})
class LobbyServiceTest {
    @Mock
    private LobbyRepository lobbyRepository;

    @InjectMocks
    private LobbyServiceImpl lobbyService;

    private Lobby lobby;

    @BeforeEach
    void setUp() {
        lobby = new Lobby();
        lobby.setGame(new Game());
        lobby.setLobbyName("name");
        lobby.setOwner(new User());
        lobby.setDescription("desc");
        lobby.setAmountPlayer(12);
    }

    @Test
    void createLobbyTest() {
        when(lobbyRepository.save(lobby)).thenReturn(lobby);
        Lobby lobby = lobbyService.create(this.lobby);
        assertEquals(this.lobby, lobby);
    }

    @Test
    void updateLobbyTest() {
        when(lobbyRepository.findById(anyLong())).thenReturn(Optional.of(lobby));
        Lobby updated = new Lobby();
        updated.setGame(new Game());
        updated.setLobbyName("name");
        updated.setOwner(new User());
        updated.setDescription("desc");
        updated.setAmountPlayer(12);
        lobbyService.update(1L, updated);
        assertNotEquals(lobby, updated);
    }

    @Test
    void deleteLobbyTest() {
        lobbyService.delete(1L);
        verify(lobbyRepository).deleteById(anyLong());
    }

    @Test
    void getLobbyTest() {
        when(lobbyRepository.getById(anyLong())).thenReturn(lobby);
        Lobby get = lobbyService.get(1L);
        assertEquals(this.lobby, get);
    }

    @Test
    void getAllLobbyTest() {
        when(lobbyRepository.findAll()).thenReturn(List.of(new Lobby()));
        List<Lobby> all = lobbyService.getAll();
        assertFalse(all.isEmpty());
    }
}
