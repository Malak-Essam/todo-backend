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
    @Test
    public void getAllMyLists_shouldReturnAllListsForUser() throws Exception {
        User user = User.builder()
            .username("malak")
            .email("malak@gmail.com")
            .password("123")
            .build();
        userRepository.save(user);
        TodoList list1 = TodoList.builder()
            .title("List 1")
            .description("First list")
            .user(user)
            .build();
        TodoList list2 = TodoList.builder()
            .title("List 2")
            .description("Second list")
            .user(user)
            .build();
        todoListRepository.save(list1);
        todoListRepository.save(list2);
        mockMvc.perform(get("/api/lists")
        .param("userId", user.getId().toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].title").value("List 1"))
            .andExpect(jsonPath("$[1].title").value("List 2"));
    }
}
