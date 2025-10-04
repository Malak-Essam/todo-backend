package com.malak.todolist.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.malak.todolist.entities.Task;
import com.malak.todolist.entities.TodoList;
import com.malak.todolist.repositories.TaskRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TaskService {

    private final UserService userService;
    private final TaskRepository taskRepository;
    private final TodoListService todoListService;
    public TaskService(TaskRepository taskRepository,TodoListService todoListService, UserService userService) {
        this.taskRepository = taskRepository;
        this.todoListService = todoListService;
        this.userService = userService;
    }
    public Task getTask(UUID id) {
        return taskRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Task not found with id: " + id)
        );
    }
    public List<Task> getAllMyTasks(UUID userId) {
        userService.getUser(userId);
        return taskRepository.findByListUserId(userId);
    }
    public List<Task> getTasksOfList(UUID listId, UUID userId) {
        TodoList list = todoListService.getList(listId, userId);
        return taskRepository.findByListId(list.getId());
    }
    public Task createTask(UUID listId, UUID userId ,Task task ) {
        TodoList list = todoListService.getList(listId, userId);
        task.setList(list);
        return taskRepository.save(task);
    }

    public void deleteTask(UUID id) {
        Task task = getTask(id);
        taskRepository.delete(task);
    }
    
    public Task updateTask(UUID id, Task updatedTask) {
        Task existingTask = getTask(id);
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setStatus(updatedTask.getStatus());
        existingTask.setDueDate(updatedTask.getDueDate());
        existingTask.setUpdatedAt(updatedTask.getUpdatedAt());
        return taskRepository.save(existingTask);
    }

}
