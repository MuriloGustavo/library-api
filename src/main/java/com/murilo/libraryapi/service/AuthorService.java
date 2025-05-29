package com.murilo.libraryapi.service;

import com.murilo.libraryapi.model.Author;
import com.murilo.libraryapi.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository repository;

    public void create(Author author) {
        repository.save(author);
    }

    public Optional<Author> findById(UUID id) {
        return repository.findById(id);
    }

    public void delete(Author author) {
        repository.delete(author);
    }

    public List<Author> search(String name, String nationality) {
        if (name != null && nationality != null) {
            return repository.findByNameAndNationality(name, nationality);
        }
        if (name != null) {
            return repository.findByName(name);
        }
        if (nationality != null) {
            return repository.findByNationality(nationality);
        }
        return repository.findAll();
    }

    public void update(Author author) {
        repository.save(author);
    }

}
