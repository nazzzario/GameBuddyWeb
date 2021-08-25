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
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/lobby")
@AllArgsConstructor
public class LobbyController {
    private final LobbyService lobbyService;
    private final UserService userService;
    private final GameService gameService;
    private final LobbyMapper lobbyMapper;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseLobby create(@RequestBody @Valid RequestLobby requestLobby, Authentication authentication) {
        Lobby lobby = lobbyMapper.requestLobbyToEntity(requestLobby);
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        User byEmail = userService.findByEmail(principal.getUsername());
        lobby.setOwner(byEmail);
        Game gameById = gameService.getGameById(requestLobby.getGameId());
        lobby.setGame(gameById);
        lobbyService.create(lobby);
        return lobbyMapper.entityToResponseLobby(lobby);
    }

    @PutMapping("/{owner_id}/edit/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == #ownerId")
    public void updateLobby(@PathVariable Long id, @PathVariable("owner_id") Long ownerId, @RequestBody RequestLobby requestLobby){
        Game gameById = gameService.getGameById(requestLobby.getGameId());
        Lobby lobby = lobbyMapper.requestLobbyToEntity(requestLobby);
        lobby.setGame(gameById);
        lobbyService.update(id, lobby);
    }

    @DeleteMapping("/{owner_id}/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == #ownerId")
    public void deleteLobby(@PathVariable Long id, @PathVariable("owner_id") Long ownerId) {
        lobbyService.delete(id);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public List<ResponseLobby> getAllLobbies(){
        return lobbyService.getAll()
                .stream()
                .map(lobbyMapper::entityToResponseLobby)
                .collect(Collectors.toList());
    }

    @GetMapping("/{lobby_id}/join/{user_id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public void joinLobby(@PathVariable("lobby_id") Long lobbyId, @PathVariable("user_id") Long userId){
        User byId = userService.findById(userId);
        lobbyService.addUserToLobby(lobbyId, byId);
    }
}
