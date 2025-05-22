package com.murilo.libraryapi.service;

import com.murilo.libraryapi.model.Author;
import com.murilo.libraryapi.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository repository;

    public Author save(Author author) {
        return repository.save(author);
    }
}
