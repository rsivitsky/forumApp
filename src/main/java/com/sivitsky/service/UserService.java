package com.sivitsky.service;

import com.sivitsky.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    Page<User> findAll(Pageable page);

    User findById(Long id);

    Optional<User> getUserByEmail(String email);

    User createUpdate(User user);

    void deleteById(Long id);
}
