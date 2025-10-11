package com.malak.todolist.services;

import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.malak.todolist.entities.User;
import com.malak.todolist.enums.Role;
import com.malak.todolist.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }
        return userRepository.save(user);
    }

    public User getUser(UUID id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User with " + id + " not found"));
    }

    public void deleteUser(UUID id) {
        User user = getUser(id);
        userRepository.delete(user);
    }
    public User updateUser(UUID id, User updatedUser) {
        User existingUser = getUser(id);
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setRole(updatedUser.getRole());
        existingUser.setEnabled(updatedUser.isEnabled());
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        return userRepository.save(existingUser);
    }
}
