package com.nkrasnovoronka.gamebuddyweb.service;

import com.nkrasnovoronka.gamebuddyweb.model.Game;

import java.util.List;
import java.util.Optional;

public interface GameService extends CrudService<Game, Long>{
    List<Game> getAllGamesByGenre(String genreName);

    Game getGameById(Long id);
}
