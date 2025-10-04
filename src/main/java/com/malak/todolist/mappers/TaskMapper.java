package com.malak.todolist.mappers;

import com.malak.todolist.dtos.TaskDto;
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
}
