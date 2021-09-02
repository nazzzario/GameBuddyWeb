package com.nkrasnovoronka.gamebuddyweb.service.impl;

import com.nkrasnovoronka.gamebuddyweb.model.Game;
import com.nkrasnovoronka.gamebuddyweb.repository.GameRepository;
import com.nkrasnovoronka.gamebuddyweb.service.GameService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        Optional<Game> byId = gameRepository.findById(id);
        if (byId.isPresent()) {
            Game game = byId.get();
            game.setGameLogo(updated.getGameLogo());
            game.setGenre(updated.getGenre());
            game.setName(updated.getName());
        }
    }

    @Override
    public void delete(Long id) {
        gameRepository.deleteById(id);
    }

    @Override
    public Game get(Long id) {
        return gameRepository.getById(id);
    }

    @Override
    public List<Game> getAll() {
        return gameRepository.findAll();
    }

    @Override
    public List<Game> getAllGamesByGenre(String genreName) {
        return gameRepository.getAllByGenreName(genreName);
    }

    @Override
    public Game getGameById(Long id) {
        return gameRepository.findById(id).orElseThrow(RuntimeException::new);
    }
}
