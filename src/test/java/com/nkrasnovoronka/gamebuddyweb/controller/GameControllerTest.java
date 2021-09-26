package com.nkrasnovoronka.gamebuddyweb.controller;

import com.nkrasnovoronka.gamebuddyweb.dto.game.RequestGame;
import com.nkrasnovoronka.gamebuddyweb.mapper.GameMapper;
import com.nkrasnovoronka.gamebuddyweb.model.Game;
import com.nkrasnovoronka.gamebuddyweb.model.Genre;
import com.nkrasnovoronka.gamebuddyweb.service.GameService;
import com.nkrasnovoronka.gamebuddyweb.service.GenreService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.nkrasnovoronka.gamebuddyweb.controller.UserControllerTest.asJsonString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"h2db", "debug"})
@AutoConfigureMockMvc
@WithMockUser(roles = {"ADMIN", "USER"})
class GameControllerTest {
    private static final String GAME_CONTROLLER_URL = "https://localhost:8080/api/v1/game";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @MockBean
    private GenreService genreService;

    @Autowired
    private GameMapper gameMapper;

    @Test
    void getGamesTest() throws Exception {
        when(gameService.get(anyLong())).thenReturn(new Game());

        MockHttpServletResponse response = mockMvc.perform(get(GAME_CONTROLLER_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }


    @Test
    void getGamesTestThrowException() throws Exception {
        doThrow(IllegalArgumentException.class)
                .when(gameService).get(anyLong());

        MockHttpServletResponse response = mockMvc.perform(get(GAME_CONTROLLER_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void getAllGamesTest() throws Exception {
        when(gameService.getAll()).thenReturn(List.of(new Game()));

        MockHttpServletResponse response = mockMvc.perform(get(GAME_CONTROLLER_URL + "/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void createGameTest() throws Exception{
        RequestGame requestGame = new RequestGame();
        requestGame.setGameLogo("test");
        requestGame.setGenreId(1L);
        requestGame.setName("test");
        when(genreService.get(anyLong())).thenReturn(new Genre());
        when(gameService.getAll()).thenReturn(List.of(new Game()));

        MockHttpServletResponse response = mockMvc.perform(post(GAME_CONTROLLER_URL + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestGame))
                )
                .andReturn().getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void deleteGameTest() throws Exception{
        doNothing().when(gameService).delete(anyLong());

        MockHttpServletResponse response = mockMvc.perform(delete(GAME_CONTROLLER_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());

    }

    @Test
    void deleteGameTestThrowException() throws Exception{
        doThrow(IllegalArgumentException.class).when(gameService).delete(anyLong());

        MockHttpServletResponse response = mockMvc.perform(delete(GAME_CONTROLLER_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());

    }

    @Test
    void updateGameTest() throws Exception{
        Genre genre = new Genre();
        genre.setGenreName("test");
        RequestGame requestGame = new RequestGame();
        requestGame.setGameLogo("test");
        requestGame.setGenreId(1L);
        requestGame.setName("test");
        requestGame.setMaxPlayers(12);

        when(genreService.get(anyLong())).thenReturn(genre);

        doNothing().when(gameService).update(anyLong(), any(Game.class));


        MockHttpServletResponse response = mockMvc.perform(put(GAME_CONTROLLER_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestGame))
                )
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

    @Test
    void updateGameTestThrowException() throws Exception{
        Genre genre = new Genre();
        genre.setGenreName("test");
        RequestGame requestGame = new RequestGame();
        requestGame.setGameLogo("test");
        requestGame.setGenreId(1L);
        requestGame.setName("test");
        requestGame.setMaxPlayers(12);

        when(genreService.get(anyLong())).thenReturn(genre);

        doThrow(IllegalArgumentException.class).when(gameService).update(anyLong(), any(Game.class));


        MockHttpServletResponse response = mockMvc.perform(put(GAME_CONTROLLER_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestGame))
                )
                .andReturn().getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());

    }

    @Test
    void getAllGamesTestBy() throws Exception {
        when(gameService.getAll()).thenReturn(List.of(new Game()));

        MockHttpServletResponse response = mockMvc.perform(get(GAME_CONTROLLER_URL + "/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("genre", "test"))
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

}
