package com.murilo.libraryapi.service;

import com.murilo.libraryapi.model.Book;
import com.murilo.libraryapi.model.GenderBook;
import com.murilo.libraryapi.repository.BookRepository;
import com.murilo.libraryapi.security.SecurityService;
import com.murilo.libraryapi.validator.BookValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.murilo.libraryapi.repository.spec.BookSpec.*;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository repository;
    private final BookValidator validator;
    private final SecurityService securityService;

    public void create(Book book) {
        validator.validate(book);
        book.setUser(securityService.findByUsername());
        repository.save(book);
    }

    public Optional<Book> findById(UUID uuid) {
        return repository.findById(uuid);
    }

    public void delete(Book book) {
        repository.delete(book);
    }

    public Page<Book> search(String isbn, String title, GenderBook gender, Integer publicationYear, String authorName,
                             Integer page, Integer pageSize) {
        Specification<Book> specs = Specification.where((root, query, cb) -> cb.conjunction());

        if (isbn != null) {
            specs = specs.and(isbnEqual(isbn));
        }

        if (title != null) {
            specs = specs.and(titleLike(title));
        }

        if (gender != null) {
            specs = specs.and(genderEqual(gender));
        }

        if (publicationYear != null) {
            specs = specs.and(publicationYearEqual(publicationYear));
        }

        if (authorName != null) {
            specs = specs.and(authorNameLike(authorName));
        }

        Pageable pageable = PageRequest.of(page, pageSize);
        return repository.findAll(specs, pageable);
    }

    public void update(Book book) {
        validator.validate(book);
        repository.save(book);
    }
}
