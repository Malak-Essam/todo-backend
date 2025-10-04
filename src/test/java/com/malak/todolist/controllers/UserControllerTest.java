package com.malak.todolist.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.malak.todolist.dtos.CreateUserDto;
import com.malak.todolist.dtos.UpdateUserDto;
import com.malak.todolist.entities.User;
import com.malak.todolist.repositories.UserRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional // to roll back after each test.
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    private void setUp() {
        userRepository.save(User.builder().username("malak").email("malak@test.com").password("123").build());
        userRepository.save(User.builder().username("john").email("john@test.com").password("456").build());
    }

    @Test
    public void getUsers_shouldReturnUsers() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].username").value("malak"));
    }

    @Test
    public void getUser_shouldReturnSingleUser() throws Exception {
        User user = userRepository.findAll().get(0);
        mockMvc.perform(get("/api/users/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("malak"));
    }

    @Test
    public void createUser_shouldReturnUser() throws Exception {
        CreateUserDto createUserDto = CreateUserDto.builder().username("malak").email("malak@test.com").password("123")
                .build();
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("malak"))
                .andExpect(jsonPath("$.email").value("malak@test.com"));
    }

    @Test
    public void updateUser_shouldReturnUpdatedUser() throws Exception {
        User user = userRepository.findAll().get(0);
        UpdateUserDto updateUserDto = UpdateUserDto.builder().username("updatedMalak").password("newPassword").build();
        mockMvc.perform(put("/api/users/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateUserDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("updatedMalak"))
                .andExpect(jsonPath("$.id").value(user.getId().toString()));
    }

    @Test
    public void updateUser_shouldReturnBadRequest_whenBodyMissing() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(put("/api/users/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getUser_shouldReturnNotFound() throws Exception {
        UUID nonExistentId = UUID.randomUUID();
        UpdateUserDto updateUserDto = UpdateUserDto.builder().username("updatedMalak").password("newPassword").build();
        mockMvc.perform(put("/api/users/" + nonExistentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateUserDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(Matchers.containsString("User with")))
                .andExpect(jsonPath("$.message").value(Matchers.containsString("not found")));
    }
    @Test
    public void deleteUser_shouldDeleteUser() throws Exception {
        User user = userRepository.findAll().get(0);
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                .delete("/api/users/" + user.getId()))
                .andExpect(status().isNoContent());
        Assertions.assertThat(userRepository.findById(user.getId())).isEmpty();
    }
}
