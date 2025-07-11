package com.murilo.libraryapi.controller;

import com.murilo.libraryapi.dto.UserDTO;
import com.murilo.libraryapi.mapper.UserMapper;
import com.murilo.libraryapi.model.User;
import com.murilo.libraryapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController implements GenericController {

    private final UserService service;
    private final UserMapper mapper;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody UserDTO userDTO) {
        User user = mapper.toEntity(userDTO);
        service.create(user);
        URI location = generatedHeaderLocation(user.getId());
        return ResponseEntity.created(location).build();
    }
}

