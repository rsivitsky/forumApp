package com.sivitsky.service;

import com.sivitsky.domain.User;
import com.sivitsky.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepo;

    @Override
    public Page<User> findAll(Pageable page) {
        return this.userRepo.findAll(page);
    }

    @Override
    public User findById(Long id) {
        return this.userRepo.findOne(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepo.findOneByEmail(email);
    }

    @Override
    public User createUpdate(User user) {
        return this.userRepo.save(user);
    }

    @Override
    public void deleteById(Long id) {
        this.userRepo.delete(id);
    }
}
