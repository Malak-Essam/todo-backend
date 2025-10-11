package com.malak.todolist.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.malak.todolist.entities.User;
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
}
