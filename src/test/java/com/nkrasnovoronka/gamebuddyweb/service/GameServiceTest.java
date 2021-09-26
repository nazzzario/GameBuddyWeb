package com.nkrasnovoronka.gamebuddyweb.service;

import com.nkrasnovoronka.gamebuddyweb.model.Game;
import com.nkrasnovoronka.gamebuddyweb.model.Genre;
import com.nkrasnovoronka.gamebuddyweb.repository.GameRepository;
import com.nkrasnovoronka.gamebuddyweb.service.impl.GameServiceImpl;
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
class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameServiceImpl gameService;

    private Game game;

    @BeforeEach
    void setUp(){
        game = new Game();
        game.setGenre(new Genre());
        game.setGameLogo("logo");
        game.setName("name");
        game.setMaxPlayers(44);
    }

    @Test
    void createGenreTest() {
        when(gameRepository.save(game)).thenReturn(game);
        Game game = gameService.create(this.game);
        assertEquals(this.game, game);
    }

    @Test
    void updateGenreTest(){
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game));
        Game updated = new Game();
        updated.setGenre(new Genre());
        updated.setGameLogo("logo");
        updated.setName("name");
        updated.setMaxPlayers(44);
        gameService.update(1L, updated);
        assertNotEquals(game, updated);
    }

    @Test
    void deleteGenreTest(){
        gameService.delete(1L);
        verify(gameRepository).deleteById(anyLong());
    }

    @Test
    void getGenreTest(){
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game));
        Game get = gameService.get(1L);
        assertEquals(this.game, get);
    }

    @Test
    void getAllGenreTest(){
        when(gameRepository.findAll()).thenReturn(List.of(new Game()));
        List<Game> all = gameService.getAll();
        assertFalse(all.isEmpty());
    }
}
