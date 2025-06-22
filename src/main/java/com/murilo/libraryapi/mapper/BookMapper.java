package com.murilo.libraryapi.mapper;

import com.murilo.libraryapi.dto.RegisterBookDTO;
import com.murilo.libraryapi.dto.SearchResultBookDTO;
import com.murilo.libraryapi.model.Book;
import com.murilo.libraryapi.repository.AuthorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AuthorMapper.class)
public abstract class BookMapper {

    @Autowired
    AuthorRepository authorRepository;

    @Mapping(target = "author", expression = "java(authorRepository.findById(bookDTO.idAuthor()).orElse(null))")
    public abstract Book toEntity(RegisterBookDTO bookDTO);

    public abstract SearchResultBookDTO toDTO(Book book);
}

