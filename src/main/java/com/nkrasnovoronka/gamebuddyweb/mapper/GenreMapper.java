package com.nkrasnovoronka.gamebuddyweb.mapper;

import com.nkrasnovoronka.gamebuddyweb.dto.GenreDTO;
import com.nkrasnovoronka.gamebuddyweb.model.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    @Mapping(target = "games", ignore = true)
    Genre toEntity(GenreDTO genreDTO);
}
