package com.murilo.libraryapi.service;

import com.murilo.libraryapi.model.User;
import com.murilo.libraryapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public void create(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);
    }

    public User findByUsername(String username) {
        return repository.findByUsername(username);
    }
}

