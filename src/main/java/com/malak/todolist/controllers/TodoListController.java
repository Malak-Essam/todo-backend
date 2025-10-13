package com.malak.todolist.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.malak.todolist.dtos.CreateTodoListDto;
import com.malak.todolist.dtos.TodoListDto;
import com.malak.todolist.dtos.UpdateTodoListDto;
import com.malak.todolist.entities.TodoList;
import com.malak.todolist.mappers.TodoListMapper;
import com.malak.todolist.security.CustomUserDetails;
import com.malak.todolist.services.TodoListService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/lists")
@Validated
public class TodoListController {
    private final TodoListService todoListService;

    public TodoListController(TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    @GetMapping
    public ResponseEntity<List<TodoListDto>> getAllMyLists(@AuthenticationPrincipal CustomUserDetails currentUser) {
        List<TodoList> todoLists = todoListService.getAllMyLists(currentUser.getUser().getId());
        List<TodoListDto> todoListDtos = todoLists.stream()
                .map(TodoListMapper::toDto)
                .toList();
        return ResponseEntity.ok(todoListDtos);
    }

    @GetMapping("/{listId}")
    public ResponseEntity<TodoListDto> getList(
            @NotNull @PathVariable UUID listId,
            @AuthenticationPrincipal CustomUserDetails currentUser) {

        TodoList list = todoListService.getList(listId, currentUser.getUser().getId());
        return ResponseEntity.ok(TodoListMapper.toDto(list));
    }

    @PostMapping
    public ResponseEntity<TodoListDto> createList(@Valid @RequestBody CreateTodoListDto list, @AuthenticationPrincipal CustomUserDetails currentUser) {
        TodoList todoList = TodoListMapper.fromCreteTodoListDto(list);
        TodoList newList = todoListService.createList(todoList, currentUser.getUser().getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(TodoListMapper.toDto(newList));
    }

    @PutMapping("/{listId}")
    public ResponseEntity<TodoListDto> updateList(
            @NotNull @PathVariable UUID listId,
            @Valid @RequestBody UpdateTodoListDto list,
            @AuthenticationPrincipal CustomUserDetails currentUser) {
        TodoList updateList = TodoListMapper.fromUpdateTodoListDto(list);
        TodoList updatedList = todoListService.updateList(listId, currentUser.getUser().getId(), updateList);
        TodoListDto updatedListDto = TodoListMapper.toDto(updatedList);
        return ResponseEntity.ok(updatedListDto);
    }

    @DeleteMapping("/{listId}")
    public ResponseEntity<Void> deleteList(@NotNull @PathVariable UUID listId, @AuthenticationPrincipal CustomUserDetails currentUser) {
        todoListService.deleteList(listId, currentUser.getUser().getId());
        return ResponseEntity.noContent().build();
    }

}
