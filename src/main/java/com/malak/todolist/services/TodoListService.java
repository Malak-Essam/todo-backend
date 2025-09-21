package com.malak.todolist.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.malak.todolist.entities.TodoList;
import com.malak.todolist.entities.User;
import com.malak.todolist.repositories.TodoListRepository;
import com.malak.todolist.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TodoListService {
    private final TodoListRepository todoListRepository;
    private final UserService userService;

    public TodoListService(TodoListRepository todoListRepository, UserService userService) {
        this.todoListRepository = todoListRepository;
        this.userService = userService;
    }

    public TodoList getList(UUID id) {
        return todoListRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("List with id " + id + " not found"));
    }

    public List<TodoList> getAllMyLists(UUID userId) {
        User user = userService.getUser(userId);
        return todoListRepository.findByUserId(userId);
    }

    public TodoList createList(TodoList todoList, UUID userId) {
        User user = userService.getUser(userId);
        todoList.setUser(user);
        return todoListRepository.save(todoList);
    }

    public void deleteList(UUID id) {
        TodoList list = getList(id);
        todoListRepository.delete(list);;
    }
    public TodoList updateList(UUID id, TodoList updatedList) {
        TodoList existingList = getList(id);
        existingList.setTitle(updatedList.getTitle());
        existingList.setDescription(updatedList.getDescription());
        return todoListRepository.save(existingList);
    }

}
