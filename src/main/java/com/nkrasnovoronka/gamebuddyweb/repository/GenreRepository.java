package com.nkrasnovoronka.gamebuddyweb.repository;

import com.nkrasnovoronka.gamebuddyweb.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
