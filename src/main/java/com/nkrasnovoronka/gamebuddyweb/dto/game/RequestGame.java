package com.nkrasnovoronka.gamebuddyweb.dto.game;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RequestGame {
    @NotBlank
    @NonNull
    private String name;
    private String gameLogo;
    @Max(value = 100)
    private Integer maxPlayers;
    private Long genreId;
}
