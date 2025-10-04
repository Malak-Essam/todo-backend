package com.malak.todolist.mappers;

import com.malak.todolist.dtos.CreateTaskDto;
import com.malak.todolist.dtos.TaskDto;
import com.malak.todolist.dtos.UpdateTaskDto;
import com.malak.todolist.entities.Task;

public class TaskMapper {
    public static TaskDto toDto(Task task) {
        if (task == null) return null;
        return TaskDto.builder()
            .id(task.getId())
            .title(task.getTitle())
            .description(task.getDescription())
            .status(task.getStatus())
            .dueDate(task.getDueDate() != null ? task.getDueDate().toString() : null)
            .listDto(TodoListMapper.toDto(task.getList()))
            .build();
    }

    public static Task toEntity(CreateTaskDto dto) {
        if (dto == null) return null;
        return Task.builder()
            .title(dto.getTitle())
            .description(dto.getDescription())
            .status(dto.getStatus())
            .dueDate(dto.getDueDate())
            .build();
    }

    public static Task toEntity(UpdateTaskDto dto) {
        if (dto == null) return null;
        return Task.builder()
            .title(dto.getTitle())
            .description(dto.getDescription())
            .status(dto.getStatus())
            .dueDate(dto.getDueDate())
            .build();
    }
}
