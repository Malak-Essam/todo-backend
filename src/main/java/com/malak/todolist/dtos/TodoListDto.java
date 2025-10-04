package com.malak.todolist.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class TodoListDto {
    private String title;
    private String description;
    private UserDto userDto;
}
