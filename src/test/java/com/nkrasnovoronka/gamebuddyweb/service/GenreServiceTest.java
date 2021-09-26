package com.nkrasnovoronka.gamebuddyweb.service;

import com.nkrasnovoronka.gamebuddyweb.model.Genre;
import com.nkrasnovoronka.gamebuddyweb.repository.GenreRepository;
import com.nkrasnovoronka.gamebuddyweb.service.impl.GenreServiceImpl;
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
class GenreServiceTest {

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GenreServiceImpl genreService;

    private Genre genre;

    @BeforeEach
    void setUp(){
        genre = new Genre();
        genre.setGenreName("test");
    }

    @Test
    void createGenreTest() {
        when(genreRepository.save(genre)).thenReturn(genre);
        Genre genre = genreService.create(this.genre);
        assertEquals(this.genre, genre);
    }

    @Test
    void updateGenreTest(){
        when(genreRepository.findById(anyLong())).thenReturn(Optional.of(genre));
        Genre updated = new Genre();
        updated.setGenreName("updated");
        genreService.update(1L, updated);
        assertNotEquals(genre, updated);
    }

    @Test
    void deleteGenreTest(){
        genreService.delete(1L);
        verify(genreRepository).deleteById(anyLong());
    }

    @Test
    void getGenreTest(){
        when(genreRepository.findById(anyLong())).thenReturn(Optional.of(genre));
        Genre get = genreService.get(1L);
        assertEquals(genre, get);
    }

    @Test
    void getAllGenreTest(){
        when(genreRepository.findAll()).thenReturn(List.of(new Genre()));
        List<Genre> all = genreService.getAll();
        assertFalse(all.isEmpty());
    }
}
