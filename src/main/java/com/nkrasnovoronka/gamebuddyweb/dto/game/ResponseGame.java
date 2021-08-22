package com.nkrasnovoronka.gamebuddyweb.dto.game;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseGame {
    private String name;
    private String gameLogo;
    private Integer maxPlayers;
    private String genre;
}
