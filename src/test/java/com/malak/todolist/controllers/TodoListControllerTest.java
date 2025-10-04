package com.malak.todolist.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.malak.todolist.dtos.CreateTodoListDto;
import com.malak.todolist.dtos.UpdateTodoListDto;
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
    @Autowired
    private ObjectMapper objectMapper;
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
    @Test
    public void createList_shouldCreateAndReturnNewList() throws Exception {
        User user = User.builder()
            .username("malak")
            .email("malak@gmail.com")
            .password("123")
            .build();
        userRepository.save(user);
        CreateTodoListDto newList = CreateTodoListDto.builder()
            .title("New List")
            .description("Created list")
            .build();
        mockMvc.perform(
            post("/api/lists")
            .param("userId", user.getId().toString())
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(newList)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.title").value("New List"))
            .andExpect(jsonPath("$.description").value("Created list"))
            .andExpect(jsonPath("$.userDto.username").value("malak"));
    }

    @Test
    public void updateList_shouldUpdateAndReturnUpdatedList() throws Exception {
        User user = User.builder()
            .username("malak")
            .email("malak@gmail.com")
            .password("123")
            .build();
        userRepository.save(user);
        TodoList list = TodoList.builder()
            .title("Old Title")
            .description("Old Description")
            .user(user)
            .build();
        todoListRepository.save(list);
        UpdateTodoListDto updatedList = UpdateTodoListDto.builder()
            .title("Updated Title")
            .description("Updated Description")
            .build();
        mockMvc.perform(
            put("/api/lists/" + list.getId())
            .param("userId", user.getId().toString())
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(updatedList)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Updated Title"))
            .andExpect(jsonPath("$.description").value("Updated Description"))
            .andExpect(jsonPath("$.userDto.username").value("malak"));
    }

    @Test
    public void deleteList_shouldDeleteList() throws Exception {
        User user = User.builder()
            .username("malak")
            .email("malak@gmail.com")
            .password("123")
            .build();
        userRepository.save(user);
        TodoList list = TodoList.builder()
            .title("To be deleted")
            .description("This list will be deleted")
            .user(user)
            .build();
        todoListRepository.save(list);
        mockMvc.perform(
            delete("/api/lists/" + list.getId())
            .param("userId", user.getId().toString()))
            .andExpect(status().isNoContent());
        // Verify the list is deleted
        Assertions.assertThat(todoListRepository.findById(list.getId())).isEmpty();
    }
}
