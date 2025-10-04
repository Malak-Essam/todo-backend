package com.malak.todolist.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.malak.todolist.dtos.CreateTaskDto;
import com.malak.todolist.dtos.TaskDto;
import com.malak.todolist.dtos.UpdateTaskDto;
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

    @GetMapping("/list/{listId}")
    public ResponseEntity<List<TaskDto>> getTasksInTodoList(@PathVariable UUID listId, @RequestParam UUID userId) {
        List<Task> tasks = taskService.getTasksOfList(listId, userId);
        List<TaskDto> taskDtos = tasks.stream()
                .map(TaskMapper::toDto)
                .toList();
        return ResponseEntity.ok().body(taskDtos);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDto> getTask(
            @PathVariable UUID taskId) {
                Task task = taskService.getTask(taskId);
                TaskDto taskDto = TaskMapper.toDto(task);

        return ResponseEntity.ok().body(taskDto);
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestParam UUID listId, @RequestParam UUID userId, @RequestBody CreateTaskDto dto) {
        Task task = TaskMapper.toEntity(dto);
        Task createdTask = taskService.createTask(listId, userId, task);
        return ResponseEntity.ok().body(TaskMapper.toDto(createdTask));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable UUID taskId, @RequestBody UpdateTaskDto dto) {
        Task task = TaskMapper.toEntity(dto);
        Task updatedTask = taskService.updateTask(taskId, task);
        return ResponseEntity.ok().body(TaskMapper.toDto(updatedTask));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }
}
