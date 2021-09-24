package com.nkrasnovoronka.gamebuddyweb.controller;

import com.nkrasnovoronka.gamebuddyweb.dto.genre.RequestGenre;
import com.nkrasnovoronka.gamebuddyweb.mapper.GenreMapper;
import com.nkrasnovoronka.gamebuddyweb.model.Genre;
import com.nkrasnovoronka.gamebuddyweb.service.GenreService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/genre")
@AllArgsConstructor
public class GenreController {
    private final Logger logger = LoggerFactory.getLogger(GenreController.class);
    private final GenreService genreService;
    private final GenreMapper genreMapper;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Genre getGenre(@PathVariable Long id) {
        logger.info("Getting genre with id {}", id);
        return genreService.get(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Genre> getAllGenre() {
        logger.info("Getting all genres");
        return genreService.getAll();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Genre create(@RequestBody @Valid RequestGenre requestGenre) {
        logger.info("Creating genre with name {}", requestGenre.getGenreName());
        Genre genre = genreMapper.toEntity(requestGenre);
        return genreService.create(genre);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void delete(@PathVariable Long id) {
        logger.info("Deleting genre with id {}", id);
        try {
            genreService.delete(id);
        } catch (IllegalArgumentException e) {
            logger.error("Cannot get genre with id {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find genre with id " + id, e);
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void update(@PathVariable Long id, @RequestBody @Valid RequestGenre requestGenre) {
        logger.info("Updating genre with id {}", id);
        Genre genre = genreMapper.toEntity(requestGenre);
        try {
            genreService.update(id, genre);
        } catch (IllegalArgumentException e) {
            logger.error("Cannot get genre with id {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find genre with id " + id, e);
        }
    }
}
