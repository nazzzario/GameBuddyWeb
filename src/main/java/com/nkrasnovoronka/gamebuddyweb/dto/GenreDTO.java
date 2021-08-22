package com.nkrasnovoronka.gamebuddyweb.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class GenreDTO {
    private Long id;
    @NotBlank
    private String genreName;
}
