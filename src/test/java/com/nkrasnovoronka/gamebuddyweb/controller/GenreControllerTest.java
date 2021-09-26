package com.nkrasnovoronka.gamebuddyweb.controller;

import com.nkrasnovoronka.gamebuddyweb.dto.genre.RequestGenre;
import com.nkrasnovoronka.gamebuddyweb.mapper.GenreMapper;
import com.nkrasnovoronka.gamebuddyweb.model.Genre;
import com.nkrasnovoronka.gamebuddyweb.service.GenreService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
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
class GenreControllerTest {
    private static final String GENRE_CONTROLLER_URL = "https://localhost:8080/api/v1/genre";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenreService genreService;

    @Autowired
    private GenreMapper genreMapper;

    @Test
    void createTest() throws Exception {
        RequestGenre requestGenre = new RequestGenre();
        requestGenre.setGenreName("test");

        when(genreService.create(ArgumentMatchers.any(Genre.class))).thenReturn(genreMapper.toEntity(any(RequestGenre.class)));

        MockHttpServletResponse response = mockMvc.perform(post(GENRE_CONTROLLER_URL + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestGenre)))
                .andReturn().getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

    }

    @Test
    void getGenreTest() throws Exception{
        when(genreService.get(anyLong())).thenReturn(new Genre());

        MockHttpServletResponse response = mockMvc.perform(get(GENRE_CONTROLLER_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

    @Test
    void getAllGenreTest() throws Exception{
        when(genreService.getAll()).thenReturn(List.of(new Genre()));

        MockHttpServletResponse response = mockMvc.perform(get(GENRE_CONTROLLER_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

    @Test
    void deleteGenreTest() throws Exception{
        doNothing().when(genreService).delete(anyLong());

        MockHttpServletResponse response = mockMvc.perform(delete(GENRE_CONTROLLER_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());

    }

    @Test
    void deleteGenreTestThrowException() throws Exception{
        doThrow(IllegalArgumentException.class).when(genreService).delete(anyLong());

        MockHttpServletResponse response = mockMvc.perform(delete(GENRE_CONTROLLER_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());

    }

    @Test
    void updateGenreTest() throws Exception{
        RequestGenre requestGenre = new RequestGenre();
        requestGenre.setGenreName("test");
        doNothing().when(genreService).update(anyLong(), any(Genre.class));


        MockHttpServletResponse response = mockMvc.perform(put(GENRE_CONTROLLER_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestGenre))
                )
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

    @Test
    void updateGenreTestThrowException() throws Exception{
        RequestGenre requestGenre = new RequestGenre();
        requestGenre.setGenreName("test");
        doThrow(IllegalArgumentException.class).when(genreService).update(anyLong(), any(Genre.class));


        MockHttpServletResponse response = mockMvc.perform(put(GENRE_CONTROLLER_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestGenre))
                )
                .andReturn().getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());

    }

}
