package com.murilo.libraryapi.controller;

import com.murilo.libraryapi.dto.RegisterBookDTO;
import com.murilo.libraryapi.dto.SearchResultBookDTO;
import com.murilo.libraryapi.mapper.BookMapper;
import com.murilo.libraryapi.model.Book;
import com.murilo.libraryapi.model.GenderBook;
import com.murilo.libraryapi.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping(value = "/books")
@RequiredArgsConstructor
public class BookController implements GenericController {
    private final BookService service;
    private final BookMapper mapper;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid RegisterBookDTO bookDTO) {
        Book book = mapper.toEntity(bookDTO);
        service.create(book);
        URI location = generatedHeaderLocation(book.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SearchResultBookDTO> findById(@PathVariable("id") String id) {
        return service.findById(UUID.fromString(id))
                .map(book -> ResponseEntity.ok(mapper.toDTO(book)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        return service.findById(UUID.fromString(id))
                .map(book -> {
                    service.delete(book);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<SearchResultBookDTO>> search(
            @RequestParam(value = "isbn", required = false) String isbn,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "gender", required = false) GenderBook gender,
            @RequestParam(value = "publication-year", required = false) Integer publicationYear,
            @RequestParam(value = "author-name", required = false) String authorName,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "page-size", defaultValue = "10") Integer pageSize
    ) {
        Page<Book> pageResult = service.search(isbn, title, gender, publicationYear, authorName, page, pageSize);
        Page<SearchResultBookDTO> result = pageResult.map(mapper::toDTO);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(
            @PathVariable("id") String id, @RequestBody @Valid RegisterBookDTO bookDTO
    ) {
        return service.findById(UUID.fromString(id))
                .map(book -> {
                    Book bookAux = mapper.toEntity(bookDTO);

                    book.setIsbn(bookAux.getIsbn());
                    book.setTitle(bookAux.getTitle());
                    book.setPublicationDate(bookAux.getPublicationDate());
                    book.setGender(bookAux.getGender());
                    book.setPrice(bookAux.getPrice());
                    book.setAuthor(bookAux.getAuthor());

                    service.update(book);

                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }}
