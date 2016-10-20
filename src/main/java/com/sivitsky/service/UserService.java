package com.sivitsky.service;

import com.sivitsky.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<User> findAll(Pageable page);

    User findById(Long id);

    User createUpdate(User user);

    void deleteById(Long id);
}
