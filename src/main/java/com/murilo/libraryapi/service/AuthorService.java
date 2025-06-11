package com.murilo.libraryapi.service;

import com.murilo.libraryapi.model.Author;
import com.murilo.libraryapi.repository.AuthorRepository;
import com.murilo.libraryapi.validator.AuthorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository repository;
    private final AuthorValidator validator;

    public void create(Author author) {
        validator.validate(author);
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

    public List<Author> searchByExample(String name, String nationality) {
        var author = new Author();
        author.setName(name);
        author.setNationality(nationality);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase("id", "registrationDate", "updateDate")
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Author> authorExample = Example.of(author, matcher);
        return repository.findAll(authorExample);
    }

    public void update(Author author) {
        validator.validate(author);
        repository.save(author);
    }

}
