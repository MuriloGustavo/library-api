package com.murilo.libraryapi.dto;

import com.murilo.libraryapi.model.Author;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AuthorDTO(
        UUID id,
        @NotBlank(message = "required field")
        @Size(min = 2, max = 100, message = "field outside standard size")
        String name,
        @NotNull(message = "required field")
        @Past(message = "can not be a future date")
        LocalDate birthDate,
        @NotBlank(message = "required field")
        @Size(min = 2, max = 50, message = "field outside standard size")
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
