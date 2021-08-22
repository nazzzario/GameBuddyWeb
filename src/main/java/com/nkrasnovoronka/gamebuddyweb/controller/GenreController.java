package com.nkrasnovoronka.gamebuddyweb.controller;

import com.nkrasnovoronka.gamebuddyweb.model.Genre;
import com.nkrasnovoronka.gamebuddyweb.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/genre")
public class GenreController {
    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Genre getGenre(@PathVariable Long id) {
        return genreService.get(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Genre> getAllGenre() {
        return genreService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Genre create(@RequestBody Genre genre) {
        return genreService.create(genre);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@PathVariable Long id) {
        genreService.delete(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable Long id, @RequestBody Genre genre) {
        genreService.update(id, genre);
    }
}
