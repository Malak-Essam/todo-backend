package com.malak.todolist.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.malak.todolist.dtos.CreateUserDto;
import com.malak.todolist.dtos.UserDto;
import com.malak.todolist.entities.User;
import com.malak.todolist.mappers.UserMapper;
import com.malak.todolist.services.UserService;


@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> getUsers() {
        return UserMapper.toDtoList(userService.getAllUsers());
    }
    
    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable UUID userId){
        return UserMapper.toDto(userService.getUser(userId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto postMethodName(@RequestBody CreateUserDto createUserDto) {
        User user = UserMapper.fromCreateUserDto(createUserDto);
        User createdUser = userService.createUser(user);
        return UserMapper.toDto(createdUser);
    }
    
    
}
