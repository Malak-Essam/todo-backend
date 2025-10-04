package com.malak.todolist.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.malak.todolist.dtos.CreateTodoListDto;
import com.malak.todolist.dtos.TodoListDto;
import com.malak.todolist.entities.TodoList;
import com.malak.todolist.mappers.TodoListMapper;
import com.malak.todolist.services.TodoListService;



@RestController
@RequestMapping("/api/lists")
public class TodoListController {
    private final TodoListService todoListService;

    public TodoListController(TodoListService todoListService) {
        this.todoListService = todoListService;
    }
    @GetMapping
    public ResponseEntity<List<TodoListDto>> getAllMyLists(@RequestParam UUID userId) {
        List<TodoList> todoLists = todoListService.getAllMyLists(userId);
        List<TodoListDto> todoListDtos = todoLists.stream()
            .map(TodoListMapper::toDto)
            .toList();
        return ResponseEntity.ok(todoListDtos);
    }
    @GetMapping("/{listId}")
    public ResponseEntity<TodoListDto> getList(
        @PathVariable UUID listId,
        @RequestParam UUID userId) {

    TodoList list = todoListService.getList(listId, userId);
    return ResponseEntity.ok(TodoListMapper.toDto(list));
}
    @PostMapping
    public ResponseEntity<TodoListDto> createList(@RequestBody CreateTodoListDto list, @RequestParam UUID userId) {
        TodoList todoList = TodoListMapper.fromCreteTodoListDto(list);
        TodoList newList = todoListService.createList(todoList, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(TodoListMapper.toDto(newList));
    }
    @PutMapping("/{listId}")
    public TodoList updateList(@PathVariable UUID listId, @RequestBody TodoList list) {
        UUID currentUserId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000"); // Simulate getting the current user's ID
        return todoListService.updateList(listId, currentUserId, list);
    }
    

    

    
}
