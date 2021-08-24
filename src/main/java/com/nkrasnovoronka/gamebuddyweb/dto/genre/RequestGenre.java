package com.nkrasnovoronka.gamebuddyweb.dto.genre;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RequestGenre {
    @NotBlank
    private String genreName;
}
