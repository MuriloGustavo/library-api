package com.murilo.libraryapi.dto;

import com.murilo.libraryapi.model.Author;

import java.time.LocalDate;
import java.util.UUID;

public record AuthorDTO(
        UUID id,
        String name,
        LocalDate birthDate,
        String nationality
) {
    public Author mapToAuthor() {
        return Author.builder()
                .name(this.name)
                .birthDate(this.birthDate)
                .nationality(this.nationality)
                .build();
    }

    public static AuthorDTO mapToAuthorDTO(Author author) {
        return new AuthorDTO(
                author.getId(),
                author.getName(),
                author.getBirthDate(),
                author.getNationality()
        );
    }
}
