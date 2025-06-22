package com.murilo.libraryapi.mapper;

import com.murilo.libraryapi.dto.AuthorDTO;
import com.murilo.libraryapi.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    @Mapping(source = "name", target = "name")
    Author toEntity(AuthorDTO authorDTO);
    AuthorDTO toDTO(Author author);
}

