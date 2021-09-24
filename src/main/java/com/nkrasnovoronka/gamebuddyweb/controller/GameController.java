package com.nkrasnovoronka.gamebuddyweb.controller;

import com.nkrasnovoronka.gamebuddyweb.dto.game.RequestGame;
import com.nkrasnovoronka.gamebuddyweb.dto.game.ResponseGame;
import com.nkrasnovoronka.gamebuddyweb.mapper.GameMapper;
import com.nkrasnovoronka.gamebuddyweb.model.Game;
import com.nkrasnovoronka.gamebuddyweb.model.Genre;
import com.nkrasnovoronka.gamebuddyweb.service.GameService;
import com.nkrasnovoronka.gamebuddyweb.service.GenreService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/game")
@AllArgsConstructor
public class GameController {
    private final Logger logger = LoggerFactory.getLogger(GameController.class);
    private final GameService gameService;
    private final GenreService genreService;
    private final GameMapper gameMapper;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public ResponseGame getGame(@PathVariable Long id) {
        logger.info("Getting game with id {}", id);
        try {
            Game game = gameService.get(id);
            return gameMapper.entityToResponse(game);
        } catch (IllegalArgumentException e) {
            logger.error("Cannot get game with id {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot found game with id " + id, e);
        }

    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public List<ResponseGame> getAllGame() {
        logger.info("Getting all games");
        return gameService.getAll()
                .stream()
                .map(gameMapper::entityToResponse)
                .collect(Collectors.toList());
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseGame create(@RequestBody @Valid RequestGame requestGame) {
        logger.info("Creating new game with name {}", requestGame.getName());
        Game game = gameMapper.requestGameToEntity(requestGame);
        Genre genre = genreService.get(requestGame.getGenreId());
        game.setGenre(genre);
        gameService.create(game);
        return gameMapper.entityToResponse(game);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void delete(@PathVariable Long id) {
        logger.info("Deleting game with id {}", id);
        try {
            gameService.delete(id);
        } catch (IllegalArgumentException e) {
            logger.error("Cannot get game with id {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot found game with id " + id, e);
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void update(@PathVariable Long id, @RequestBody @Valid RequestGame requestGame) {
        logger.info("Updating game with id {}", id);
        Genre genre = genreService.get(requestGame.getGenreId());
        Game game = gameMapper.requestGameToEntity(requestGame);
        game.setGenre(genre);
        try {
            gameService.update(id, game);
        } catch (IllegalArgumentException e) {
            logger.error("Cannot get game with id {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot found game with id " + id, e);
        }
    }

    @GetMapping(value = "/all", params = "genre")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public List<ResponseGame> getAllGamesByGenre(@RequestParam(name = "genre") String genre) {
        logger.info("Getting all games with genre {}", genre);
        return gameService.getAllGamesByGenre(genre)
                .stream()
                .map(gameMapper::entityToResponse)
                .collect(Collectors.toList());
    }
}
 
