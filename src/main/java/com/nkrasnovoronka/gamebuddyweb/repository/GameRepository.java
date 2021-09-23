package com.nkrasnovoronka.gamebuddyweb.repository;


import com.nkrasnovoronka.gamebuddyweb.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    @Query("select g from Game g where g.genre.genreName = :genreName")
    List<Game> getAllByGenreName(String genreName);
}
