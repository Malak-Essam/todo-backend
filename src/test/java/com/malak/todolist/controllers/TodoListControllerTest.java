package com.malak.todolist.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.malak.todolist.entities.TodoList;
import com.malak.todolist.entities.User;
import com.malak.todolist.repositories.TodoListRepository;
import com.malak.todolist.repositories.UserRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TodoListControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TodoListRepository todoListRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        todoListRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void getList_shouldReturnTodoList() throws Exception {
        User user = User.builder()
            .username("malak")
            .email("malak@test.com")
            .password("123")
            .build();
        
        userRepository.save(user);

        TodoList list = TodoList.builder()
            .title("My List")
            .description("Testing list")
            .user(user)
            .build();
        todoListRepository.save(list);

        mockMvc.perform(get("/api/lists/" + list.getId())
        .param("userId", user.getId().toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("My List"))
            .andExpect(jsonPath("$.description").value("Testing list"))
            .andExpect(jsonPath("$.userDto.username").value("malak"));
            
    }
}
