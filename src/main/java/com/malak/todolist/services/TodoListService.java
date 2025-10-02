package com.malak.todolist.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.malak.todolist.entities.TodoList;
import com.malak.todolist.entities.User;
import com.malak.todolist.repositories.TodoListRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TodoListService {
    private final TodoListRepository todoListRepository;
    private final UserService userService;

    public TodoListService(TodoListRepository todoListRepository, UserService userService) {
        this.todoListRepository = todoListRepository;
        this.userService = userService;
    }

    public TodoList getList(UUID listId, UUID userId) {
        return todoListRepository.findByIdAndUserId(listId, userId).orElseThrow(
                () -> new EntityNotFoundException("List with id " + listId + " not found or access denied"));
    }

    public List<TodoList> getAllMyLists(UUID userId) {
        return todoListRepository.findByUserId(userId);
    }

    public TodoList createList(TodoList todoList, UUID userId) {
        User user = userService.getUser(userId);
        todoList.setUser(user);
        return todoListRepository.save(todoList);
    }

    public void deleteList(UUID listId, UUID userId) {
        TodoList list = getList(listId, userId);
        todoListRepository.delete(list);
    }
    public void deleteTodoListsByUser(User user) {
        todoListRepository.deleteByUserId(user.getId());
    }
    public TodoList updateList(UUID listId, UUID userId, TodoList updatedList) {
        TodoList existingList = getList(listId, userId);
        existingList.setTitle(updatedList.getTitle());
        existingList.setDescription(updatedList.getDescription());
        return todoListRepository.save(existingList);
    }

}
