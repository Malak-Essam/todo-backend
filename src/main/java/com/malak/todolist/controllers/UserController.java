package com.malak.todolist.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.malak.todolist.dtos.CreateUserDto;
import com.malak.todolist.dtos.UpdateUserDto;
import com.malak.todolist.dtos.UserDto;
import com.malak.todolist.entities.User;
import com.malak.todolist.mappers.UserMapper;
import com.malak.todolist.services.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> users = UserMapper.toDtoList(userService.getAllUsers());
        return ResponseEntity.ok().body(users);
    }
    
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable @NotNull UUID userId){
        UserDto userDto = UserMapper.toDto(userService.getUser(userId));
        return ResponseEntity.ok().body(userDto);
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserDto createUserDto) {
        User user = UserMapper.fromCreateUserDto(createUserDto);
        User createdUser = userService.createUser(user);
        UserDto userDto = UserMapper.toDto(createdUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@NotNull @PathVariable UUID id, @Valid @RequestBody UpdateUserDto updateUserDto) {
        User user = UserMapper.fromUpdateUserDto(updateUserDto);
        User updated = userService.updateUser(id, user);
        UserDto userDto = UserMapper.toDto(updated);
        return ResponseEntity.ok().body(userDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@NotNull @PathVariable UUID id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
