package com.nkrasnovoronka.gamebuddyweb.controller;

import com.nkrasnovoronka.gamebuddyweb.dto.game.RequestGame;
import com.nkrasnovoronka.gamebuddyweb.dto.game.ResponseGame;
import com.nkrasnovoronka.gamebuddyweb.mapper.GameMapper;
import com.nkrasnovoronka.gamebuddyweb.model.Game;
import com.nkrasnovoronka.gamebuddyweb.model.Genre;
import com.nkrasnovoronka.gamebuddyweb.service.GameService;
import com.nkrasnovoronka.gamebuddyweb.service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/game")
@AllArgsConstructor
public class GameController {
    private final GameService gameService;
    private final GenreService genreService;
    private final GameMapper gameMapper;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Game getGame(@PathVariable Long id) {
        return gameService.get(id);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ResponseGame> getAllGame() {
        return gameService.getAll()
                .stream()
                .map(gameMapper::entityToResponse)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseGame create(@RequestBody @Valid RequestGame requestGame) {
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
    public void delete(@PathVariable Long id) {
        gameService.delete(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable Long id, @RequestBody @Valid RequestGame requestGame) {
        Game game = gameMapper.requestGameToEntity(requestGame);
        gameService.update(id, game);
    }

    @GetMapping(value = "/all", params = "genre")
    @ResponseStatus(HttpStatus.OK)
    public List<ResponseGame> getAllGamesByGenre(@RequestParam(name = "genre") String genre){
        return gameService.getAllGamesByGenre(genre)
                .stream()
                .map(gameMapper::entityToResponse)
                .collect(Collectors.toList());
    }
}
