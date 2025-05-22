package com.murilo.libraryapi.controller;

import com.murilo.libraryapi.dto.AuthorDTO;
import com.murilo.libraryapi.model.Author;
import com.murilo.libraryapi.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService service;

    public ResponseEntity<Void> create(@RequestBody AuthorDTO authorDTO) {
        Author author = authorDTO.mapToAuthor();
        service.save(author);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(author.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

}
