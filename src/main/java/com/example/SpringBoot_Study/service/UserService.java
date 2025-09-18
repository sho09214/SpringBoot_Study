package com.example.SpringBoot_Study.service;

import com.example.SpringBoot_Study.model.User;
import com.example.SpringBoot_Study.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User getCurrentUser() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return repository.findByUsername(username).orElseThrow();
        }
        throw new IllegalStateException("User not logged in");
    }
}
