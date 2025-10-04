package com.malak.todolist.dtos;

import java.util.UUID;

import com.malak.todolist.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class TaskDto {
    private UUID id;
    private String title;
    private String description;
    private Status status;
    private String dueDate;
    private TodoListDto listDto;
}
