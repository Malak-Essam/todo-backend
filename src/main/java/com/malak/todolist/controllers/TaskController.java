package com.malak.todolist.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.malak.todolist.dtos.TaskDto;
import com.malak.todolist.entities.Task;
import com.malak.todolist.mappers.TaskMapper;
import com.malak.todolist.services.TaskService;


@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getMyTasks(@RequestParam UUID userId) {
        List<Task> tasks = taskService.getAllMyTasks(userId);
        List<TaskDto> taskDtos = tasks.stream()
            .map(TaskMapper::toDto)
            .toList();
        return ResponseEntity.ok(taskDtos);
    }
    
    
}
