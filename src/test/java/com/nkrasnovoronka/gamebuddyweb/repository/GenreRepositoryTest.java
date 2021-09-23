package com.nkrasnovoronka.gamebuddyweb.repository;

import com.nkrasnovoronka.gamebuddyweb.model.Genre;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

//@Transactional
//@SpringBootTest
@Slf4j
@DataJpaTest
@ActiveProfiles({"h2db", "test"})
class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    private Genre genre;

    @BeforeEach
    void setUp() {
        genre = new Genre();
        genre.setGenreName("test");
    }

    @Test
    void findSavedUserById() {
        Genre save = genreRepository.save(genre);
        Optional<Genre> byId = genreRepository.findById(save.getId());
        assertThat(byId).hasValue(save);
    }
}
