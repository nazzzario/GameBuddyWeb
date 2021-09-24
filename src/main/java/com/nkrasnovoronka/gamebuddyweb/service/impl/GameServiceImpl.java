package com.nkrasnovoronka.gamebuddyweb.service.impl;

import com.nkrasnovoronka.gamebuddyweb.model.Game;
import com.nkrasnovoronka.gamebuddyweb.repository.GameRepository;
import com.nkrasnovoronka.gamebuddyweb.service.GameService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;

    @Override
    public Game create(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public void update(Long id, Game updated) {
        Game game = gameRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        game.setGameLogo(updated.getGameLogo());
        game.setGenre(updated.getGenre());
        game.setName(updated.getName());
    }

    @Override
    public void delete(Long id) {
        gameRepository.deleteById(id);
    }

    @Override
    public Game get(Long id) {
        return gameRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Game> getAll() {
        return gameRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Game> getAllGamesByGenre(String genreName) {
        return gameRepository.getAllByGenreName(genreName);
    }

    @Override
    public Game getGameById(Long id) {
        return gameRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
}
