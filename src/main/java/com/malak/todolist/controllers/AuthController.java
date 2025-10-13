package com.malak.todolist.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.malak.todolist.dtos.AuthResponse;
import com.malak.todolist.dtos.CreateUserDto;
import com.malak.todolist.dtos.LoginRequest;
import com.malak.todolist.entities.User;
import com.malak.todolist.mappers.UserMapper;
import com.malak.todolist.security.CustomUserDetails;
import com.malak.todolist.security.JwtService;
import com.malak.todolist.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
public ResponseEntity<?> register(@Valid @RequestBody CreateUserDto dto) {
    User user = UserMapper.fromCreateUserDto(dto);
    User createdUser = userService.createUser(user);

    // Generate JWT for the new user
    String token = jwtService.generateToken(createdUser.getUsername(), createdUser.getRole());

    // You can return both user info and token if you want
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(Map.of(
                    "user", UserMapper.toDto(createdUser),
                    "token", token
            ));
}

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            String jwtToken = jwtService.generateToken(authentication.getName(), userDetails.getUser().getRole());

            return ResponseEntity.ok().body(new AuthResponse(jwtToken));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

}
