package com.nkrasnovoronka.gamebuddyweb.service.impl;

import com.nkrasnovoronka.gamebuddyweb.model.Genre;
import com.nkrasnovoronka.gamebuddyweb.repository.GenreRepository;
import com.nkrasnovoronka.gamebuddyweb.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public Genre create(Genre genre) {
        genreRepository.save(genre);
        return genre;
    }

    @Override
    public void update(Long id, Genre updated) {
        Genre genre = genreRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        genre.setGenreName(updated.getGenreName());
    }

    @Override
    public void delete(Long id) {
        genreRepository.deleteById(id);
    }

    @Override
    public Genre get(Long id) {
        return genreRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

}
