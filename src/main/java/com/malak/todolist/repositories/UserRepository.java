package com.malak.todolist.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.malak.todolist.entities.User;
public interface UserRepository extends JpaRepository<User, UUID> {

}
