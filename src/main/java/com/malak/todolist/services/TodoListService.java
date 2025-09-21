package com.malak.todolist.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.malak.todolist.entities.TodoList;
import com.malak.todolist.repositories.TodoListRepository;

@Service
public class TodoListService {
    private final TodoListRepository todoListRepository;
    public TodoListService(TodoListRepository todoListRepository) {
        this.todoListRepository = todoListRepository;
    }
    public List<TodoList> getAllLists() {
        return todoListRepository.findAll();
    }
}
