package com.malak.todolist.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.malak.todolist.entities.Task;
import com.malak.todolist.entities.TodoList;
import com.malak.todolist.entities.User;
import com.malak.todolist.enums.Status;
import com.malak.todolist.services.TaskService;
import com.malak.todolist.services.TodoListService;
import com.malak.todolist.services.UserService;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TaskControllerTest {
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;
    @Autowired
    private TodoListService todoListService;
    
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getMyTasks_shouldReturnTasks() throws Exception {
        User user = User.builder()
            .username("malak")
            .email("malak@gmail.com")
            .password("123456")
            .build();
        userService.createUser(user);
        TodoList list = TodoList.builder()
            .title("My List")
            .description("This is my list")
            .user(user)
            .build();
        TodoList createdList = todoListService.createList(list,user.getId());
        Task task = Task.builder()
            .title("Test Task")
            .description("This is a test task")
            .status(Status.IN_PROGRESS)
            .dueDate(LocalDateTime.now().plusDays(1))
            .list(list)
            .build();
        taskService.createTask(list.getId(), user.getId(), task);

        mockMvc.perform(get("/api/tasks")
            .param("userId", user.getId().toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$[0].listDto.id").value(createdList.getId().toString()));
    }
    @Test
    public void getTasksINTodoList_shouldReturnListOfTaskDtos() throws Exception {
        User user = User.builder()
            .username("malak")
            .email("malak@gmail.com")
            .password("123456")
            .build();
        userService.createUser(user);
        TodoList list = TodoList.builder()
            .title("My List")
            .description("This is my list")
            .user(user)
            .build();
        TodoList createdList = todoListService.createList(list,user.getId());
        Task task1 = Task.builder()
            .title("Test Task")
            .description("This is a test task")
            .status(Status.IN_PROGRESS)
            .dueDate(LocalDateTime.now().plusDays(1))
            .list(list)
            .build();
        Task task2 = Task.builder()
            .title("Test Task 2")
            .description("This is another test task")
            .status(Status.PENDING)
            .dueDate(LocalDateTime.now().plusDays(2))
            .list(list)
            .build();
        taskService.createTask(list.getId(), user.getId(), task1);
        taskService.createTask(list.getId(), user.getId(), task2);

        mockMvc.perform(get("/api/tasks/" + createdList.getId().toString())
            .param("userId", user.getId().toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].listDto.id").value(createdList.getId().toString()));
    }
}
