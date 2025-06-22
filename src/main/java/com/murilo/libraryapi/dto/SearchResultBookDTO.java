package com.murilo.libraryapi.dto;

import com.murilo.libraryapi.model.GenderBook;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record SearchResultBookDTO(
        UUID id,
        String isbn,
        String title,
        LocalDate publicationDate,
        GenderBook gender,
        BigDecimal price,
        AuthorDTO author
) {
}
