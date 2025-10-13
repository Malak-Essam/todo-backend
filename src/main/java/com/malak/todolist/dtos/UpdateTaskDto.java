package com.malak.todolist.dtos;

import java.time.LocalDateTime;

import com.malak.todolist.enums.Status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateTaskDto {
    @NotBlank(message = "title is required")
    private String title;
    @Size(max = 250, message = "description can't exceed 250 char")
    private String description;
    @NotNull(message = "status can't be null")
    private Status status;
    @NotNull(message = "dueDate can't be null")
    private LocalDateTime dueDate;
}
