package com.nkrasnovoronka.gamebuddyweb.controller;

import com.nkrasnovoronka.gamebuddyweb.dto.lobby.RequestLobby;
import com.nkrasnovoronka.gamebuddyweb.dto.lobby.ResponseLobby;
import com.nkrasnovoronka.gamebuddyweb.mapper.LobbyMapper;
import com.nkrasnovoronka.gamebuddyweb.model.Game;
import com.nkrasnovoronka.gamebuddyweb.model.Lobby;
import com.nkrasnovoronka.gamebuddyweb.model.user.User;
import com.nkrasnovoronka.gamebuddyweb.service.GameService;
import com.nkrasnovoronka.gamebuddyweb.service.LobbyService;
import com.nkrasnovoronka.gamebuddyweb.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/lobby")
@AllArgsConstructor
public class LobbyController {
    private final Logger logger = LoggerFactory.getLogger(LobbyController.class);
    private final LobbyService lobbyService;
    private final UserService userService;
    private final GameService gameService;
    private final LobbyMapper lobbyMapper;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseLobby create(@RequestBody @Valid RequestLobby requestLobby, Authentication authentication) {
        logger.info("User {} creating lobby with name {}", authentication.getName(), requestLobby.getLobbyName());
        try {
            Lobby lobby = lobbyMapper.requestLobbyToEntity(requestLobby);
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            User byEmail = userService.findByEmail(principal.getUsername());
            Game gameById = gameService.getGameById(requestLobby.getGameId());
            lobby.setOwner(byEmail);
            lobby.setGame(gameById);
            lobbyService.create(lobby);
            return lobbyMapper.entityToResponseLobby(lobby);
        } catch (IllegalArgumentException e) {
            logger.error("Cannot create lobby {}", requestLobby.getLobbyName());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot create lobby", e);
        }
    }

    @PutMapping("/{owner_id}/edit/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == #ownerId")
    public void updateLobby(@PathVariable Long id, @PathVariable("owner_id") Long ownerId, @RequestBody @Valid RequestLobby requestLobby) {
        logger.info("Updating creating lobby with name {}", requestLobby.getLobbyName());
        try {
            Game gameById = gameService.getGameById(requestLobby.getGameId());
            Lobby lobby = lobbyMapper.requestLobbyToEntity(requestLobby);
            lobby.setGame(gameById);
            lobbyService.update(id, lobby);
        } catch (IllegalArgumentException e) {
            logger.error("Cannot create lobby {}", requestLobby.getLobbyName());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot create lobby", e);
        }
    }

    @DeleteMapping("/{owner_id}/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == #ownerId")
    public void deleteLobby(@PathVariable Long id, @PathVariable("owner_id") Long ownerId) {
        try {
            logger.info("Deleting lobby with id {}", id);
            lobbyService.delete(id);
        } catch (IllegalArgumentException e) {
            logger.error("Cannot find lobby {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find lobby", e);
        }
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public List<ResponseLobby> getAllLobbies() {
        logger.info("Getting all lobbies");
        return lobbyService.getAll()
                .stream()
                .map(lobbyMapper::entityToResponseLobby)
                .collect(Collectors.toList());
    }

    @GetMapping("/{lobby_id}/join/{user_id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public void joinLobby(@PathVariable("lobby_id") Long lobbyId, @PathVariable("user_id") Long userId) {
        logger.info("User {} join lobby {}", userId, lobbyId);
        try {
            User user = userService.findById(userId);
            lobbyService.addUserToLobby(lobbyId, user);
        } catch (IllegalArgumentException e) {
            logger.error("Cannot find lobby {}", lobbyId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find lobby", e);
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public List<ResponseLobby> getLobbiesByGameName(@RequestParam("gameName") String gameName) {
        logger.info("Getting all lobbies with game {}", gameName);
        return lobbyService.findAllLobbiesByGameName(gameName)
                .stream()
                .map(lobbyMapper::entityToResponseLobby)
                .collect(Collectors.toList());
    }
}
