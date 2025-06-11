package com.murilo.libraryapi.controller;

import com.murilo.libraryapi.dto.AuthorDTO;
import com.murilo.libraryapi.model.Author;
import com.murilo.libraryapi.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService service;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid AuthorDTO authorDTO) {
        Author author = authorDTO.mapToAuthor();
        service.create(author);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(author.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> findById(@PathVariable("id") String id) {
        return service.findById(UUID.fromString(id))
                .map(author -> ResponseEntity.ok(AuthorDTO.mapToAuthorDTO(author)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        return service.findById(UUID.fromString(id))
                .map(author -> {
                    service.delete(author);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> search(
        @RequestParam(value = "name", required = false) String name,
        @RequestParam(value = "nationality", required = false) String nationality
    ) {
        List<AuthorDTO> authors = service.searchByExample(name, nationality)
                .stream()
                .map(AuthorDTO::mapToAuthorDTO)
                .toList();

       return ResponseEntity.ok(authors);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(
            @PathVariable("id") String id, @RequestBody @Valid AuthorDTO authorDTO
    ) {
        return service.findById(UUID.fromString(id))
                .map(author -> {
                    author.setName(authorDTO.name());
                    author.setNationality(authorDTO.nationality());
                    author.setBirthDate(authorDTO.birthDate());
                    service.update(author);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
