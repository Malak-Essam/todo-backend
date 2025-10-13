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
public class CreateTaskDto {
    @NotBlank(message = "title is required")
    private String title;
    @Size(max = 250, message = "description can not exceed 250 char")
    private String description;
    @NotNull(message = "Status can not be null")
    private Status status;
    @NotNull
    private LocalDateTime dueDate;
}
