package com.malak.todolist.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.malak.todolist.entities.TodoList;
import com.malak.todolist.services.TodoListService;



@RestController
@RequestMapping("/api/lists")
public class TodoListController {
    private final TodoListService todoListService;

    public TodoListController(TodoListService todoListService) {
        this.todoListService = todoListService;
    }
    @GetMapping
    public List<TodoList> getAllMyLists() {
        // this is just a placeholder
        UUID currentUserId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000"); // Simulate getting the current user's ID
        return todoListService.getAllMyLists(currentUserId);
    }
    @GetMapping("/{listId}")
    public TodoList getList(@PathVariable UUID listId) {
        // this is just a placeholder
        UUID currentUserId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000"); // Simulate getting the current user's ID
        return todoListService.getList(listId, currentUserId);
    }
    @PostMapping
    public TodoList createList(@RequestBody TodoList list) {
        UUID currentUserId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000"); // Simulate getting the current user's ID
        TodoList newList = todoListService.createList(list, currentUserId);
        return newList;
    }
    @PutMapping("/{listId}")
    public TodoList updateList(@PathVariable UUID listId, @RequestBody TodoList list) {
        UUID currentUserId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000"); // Simulate getting the current user's ID
        return todoListService.updateList(listId, currentUserId, list);
    }
    

    

    
}
