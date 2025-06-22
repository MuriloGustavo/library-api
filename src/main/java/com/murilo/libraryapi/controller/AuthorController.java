package com.murilo.libraryapi.controller;

import com.murilo.libraryapi.dto.AuthorDTO;
import com.murilo.libraryapi.mapper.AuthorMapper;
import com.murilo.libraryapi.model.Author;
import com.murilo.libraryapi.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/authors")
@RequiredArgsConstructor
public class AuthorController implements GenericController {
    private final AuthorService service;
    private final AuthorMapper mapper;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid AuthorDTO authorDTO) {
        Author author = mapper.toEntity(authorDTO);
        service.create(author);
        URI location = generatedHeaderLocation(author.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> findById(@PathVariable("id") String id) {
        return service.findById(UUID.fromString(id))
                .map(author -> ResponseEntity.ok(mapper.toDTO(author)))
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
                .map(mapper::toDTO)
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
