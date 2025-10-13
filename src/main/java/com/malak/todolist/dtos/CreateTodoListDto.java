package com.malak.todolist.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class CreateTodoListDto {
    @NotBlank(message = "title is required")
    private String title;
    @Size(max = 250, message = "description can not exceed 250 char")
    private String description;
}
