package com.malak.todolist.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.malak.todolist.entities.Task;
import com.malak.todolist.repositories.TaskRepository;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}
