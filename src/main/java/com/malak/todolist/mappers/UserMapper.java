package com.malak.todolist.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.malak.todolist.dtos.CreateUserDto;
import com.malak.todolist.dtos.UserDto;
import com.malak.todolist.entities.User;

public class UserMapper {
    public static UserDto toDto(User user) {
        if (user == null)
            return null;
        return UserDto.builder()
        .id(user.getId())
        .username(user.getUsername())
        .email(user.getEmail())
        .build();
    }
    public static List<UserDto> toDtoList(List<User> users){
        return users.stream()
            .map(UserMapper::toDto)
            .collect(Collectors.toList());
    }

    public static User fromCreateUserDto(CreateUserDto createUserDto){
        if (createUserDto == null) return null;
        return User.builder()
        .username(createUserDto.getUsername())
        .email(createUserDto.getEmail())
        .password(createUserDto.getPassword())
        .build();
    }
}
