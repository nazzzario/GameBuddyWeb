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
        return gameMapper.entityToResponse(gameService.get(id));
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
        logger.info("Creating new game");
        Game game = gameMapper.requestGameToEntity(requestGame);
        Genre genre = genreService.get(requestGame.getGenreId());
        if (genre != null) {
            game.setGenre(genre);
        }

        gameService.create(game);
        return gameMapper.entityToResponse(game);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void delete(@PathVariable Long id) {
        gameService.delete(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void update(@PathVariable Long id, @RequestBody @Valid RequestGame requestGame) {
        Game game = gameMapper.requestGameToEntity(requestGame);
        gameService.update(id, game);
    }

    @GetMapping(value = "/all", params = "genre")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public List<ResponseGame> getAllGamesByGenre(@RequestParam(name = "genre") String genre) {
        return gameService.getAllGamesByGenre(genre)
                .stream()
                .map(gameMapper::entityToResponse)
                .collect(Collectors.toList());
    }
}
 
