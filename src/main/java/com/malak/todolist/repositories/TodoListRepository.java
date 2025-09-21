package com.malak.todolist.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.malak.todolist.entities.TodoList;

public interface TodoListRepository extends JpaRepository<TodoList, UUID> {
    public List<TodoList> findByUserId(UUID userId);
}
