package com.nkrasnovoronka.gamebuddyweb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "genres")
@Getter
@Setter
public class Genre extends BaseEntity {
    @Column(unique = true, name = "genre_name", nullable = false)
    private String genreName;

    @OneToMany(mappedBy = "genre", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Game> games;

    public Genre() {
        games = new HashSet<>();
    }

    public void addGameToGenre(Game game) {
        games.add(game);
        game.setGenre(this);
    }
}
