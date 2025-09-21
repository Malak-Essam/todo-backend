package com.malak.todolist.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.malak.todolist.entities.User;
import com.malak.todolist.repositories.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
