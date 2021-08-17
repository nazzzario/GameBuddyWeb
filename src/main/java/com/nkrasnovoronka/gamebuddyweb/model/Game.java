package com.nkrasnovoronka.gamebuddyweb.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "games")
@Getter
@Setter
public class Game extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String name;

    @Column(name = "game_logo")
    private String gameLogo;

    @OneToOne(mappedBy = "game", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Lobby lobby;

    @Column(name = "max_players")
    private Integer maxPlayers;

    @ManyToOne
    @JoinColumn(name = "genre_id", nullable = false)
    private Genre genre;


}
