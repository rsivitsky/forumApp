package com.sivitsky.service;

import com.sivitsky.domain.User;
import com.sivitsky.domain.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<User> findAll(Pageable page);

    User findById(Long id);

    User getUserByEmail(String email);

    User createUpdate(User user);

    User registerNewUserAccount(UserDto user);

    void deleteById(Long id);
}
