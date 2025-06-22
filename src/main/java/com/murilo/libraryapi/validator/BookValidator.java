package com.murilo.libraryapi.validator;

import com.murilo.libraryapi.exception.DuplicateRegisterException;
import com.murilo.libraryapi.exception.InvalidFieldException;
import com.murilo.libraryapi.model.Book;
import com.murilo.libraryapi.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookValidator {
    private final BookRepository repository;

    public void validate(Book book) {
        if (existsBookWithIsbn(book)) {
            throw new DuplicateRegisterException("ISBN already registered");
        }

        if (isPriceRequired(book)) {
            throw new InvalidFieldException("price", "For books published from 2020 onwards, the price is mandatory");
        }
    }

    private boolean existsBookWithIsbn(Book book) {
        Optional<Book> bookFound = repository.findByIsbn(book.getIsbn());

        if (book.getId() == null) return bookFound.isPresent();

        return bookFound
                .map(Book::getId)
                .stream()
                .anyMatch(id -> !id.equals(book.getId()));
    }

    private boolean isPriceRequired(Book book) {
        return book.getPrice() == null && book.getPublicationDate().getYear() >= 2020;
    }
}
