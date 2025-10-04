package com.malak.todolist.dtos;

import java.time.LocalDateTime;

import com.malak.todolist.enums.Status;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateTaskDto {
    private String title;
    private String description;
    private Status status;
    private LocalDateTime dueDate;
}
