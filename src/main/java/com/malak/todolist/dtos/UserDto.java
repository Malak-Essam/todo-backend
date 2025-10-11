package com.malak.todolist.dtos;

import java.util.UUID;

import com.malak.todolist.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class UserDto {
    private UUID id;
    private String username;
    private String email;
    private Role role;
    private boolean enabled;

}
