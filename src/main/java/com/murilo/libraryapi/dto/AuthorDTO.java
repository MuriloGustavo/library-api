package com.murilo.libraryapi.dto;

import com.murilo.libraryapi.model.Author;

import java.time.LocalDate;

public record AuthorDTO(
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
}
