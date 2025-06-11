package com.murilo.libraryapi.validator;

import com.murilo.libraryapi.exception.DuplicateRegisterException;
import com.murilo.libraryapi.model.Author;
import com.murilo.libraryapi.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthorValidator {
    private final AuthorRepository repository;

    public void validate(Author author) {
        if (existsRegisteredAuthor(author)) {
            throw new DuplicateRegisterException("Author already registered");
        }
    }

    private boolean existsRegisteredAuthor(Author author) {
        Optional<Author> authorFound = repository.findByNameAndBirthDateAndNationality(
                author.getName(), author.getBirthDate(), author.getNationality()
        );

        if (authorFound.isEmpty()) return false;

        if (author.getId() == null) return true;

        return !author.getId().equals(authorFound.get().getId());
    }
}
