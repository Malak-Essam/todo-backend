package com.malak.todolist.mappers;

import com.malak.todolist.dtos.CreateTodoListDto;
import com.malak.todolist.dtos.TodoListDto;
import com.malak.todolist.dtos.UpdateTodoListDto;
import com.malak.todolist.entities.TodoList;

public class TodoListMapper {
    public static TodoListDto toDto(TodoList list) {
        if (list == null)
            return null;
        return TodoListDto.builder()
                .title(list.getTitle())
                .description(list.getDescription())
                .userDto(UserMapper.toDto(list.getUser()))
                .build();
    }

    public static TodoList fromCreteTodoListDto(CreateTodoListDto createTodoListDto) {
        if (createTodoListDto == null)
            return null;
        return TodoList.builder()
                .title(createTodoListDto.getTitle())
                .description(createTodoListDto.getDescription())
                .build();
    }

    public static TodoList fromUpdateTodoListDto(UpdateTodoListDto updateTodoListDto) {
        if (updateTodoListDto == null)
            return null;
        TodoList list = TodoList.builder()
                .title(updateTodoListDto.getTitle())
                .description(updateTodoListDto.getDescription())
                .build();
        return list;
    }

}
