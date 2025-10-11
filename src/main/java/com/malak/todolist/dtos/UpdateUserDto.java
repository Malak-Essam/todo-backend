package com.malak.todolist.dtos;
import com.malak.todolist.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class UpdateUserDto {
    private String username;
    private String password;
    private Role role;
    private boolean enabled;
}
