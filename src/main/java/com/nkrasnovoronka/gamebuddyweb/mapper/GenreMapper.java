package com.nkrasnovoronka.gamebuddyweb.mapper;

import com.nkrasnovoronka.gamebuddyweb.dto.genre.RequestGenre;
import com.nkrasnovoronka.gamebuddyweb.model.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    @Mapping(target = "games", ignore = true)
    Genre toEntity(RequestGenre requestGenre);
}
