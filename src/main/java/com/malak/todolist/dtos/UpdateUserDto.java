package com.malak.todolist.dtos;
import com.malak.todolist.enums.Role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class UpdateUserDto {
    @NotBlank(message = "username is required")
    private String username;
    @NotBlank(message = "password is required")
    @Size(min = 8, message = "password can't be smaller than 8 chars")
    private String password;
    @NotNull(message = "role can't be null")
    private Role role;
    @NotNull(message = "enabled can't be null")
    private boolean enabled;
}
