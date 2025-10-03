package com.malak.todolist.services;

import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.malak.todolist.entities.TodoList;
import com.malak.todolist.entities.User;
import com.malak.todolist.repositories.TodoListRepository;
import com.malak.todolist.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
public class TodoListServiceTest {

    @Autowired
    TodoListService todoListService;

    @Autowired
    TodoListRepository todoListRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void getList_shouldThrowEntityNotFoundException(){
        // arrange
        UUID listId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        Assertions.assertThatThrownBy(() -> todoListService.getList(listId, userId))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage("List with id " + listId + " not found or access denied");
    }
    @Test
    public void getList_shouldReturnTodoList(){
        // arrange
        User user = User.builder().username("malak").email("malak@test.com").password("123").build();
        userRepository.save(user);
        TodoList list = TodoList.builder().title("myList").user(user).build();
        todoListRepository.save(list);
        // act
        TodoList foundedTodoList = todoListService.getList(list.getId(), user.getId());
        // Assert
        Assertions.assertThat(foundedTodoList).isNotNull();
        Assertions.assertThat(foundedTodoList.getId()).isEqualTo(list.getId());
        Assertions.assertThat(foundedTodoList.getTitle()).isEqualTo("myList");
        Assertions.assertThat(foundedTodoList.getUser().getId()).isEqualTo(user.getId());
    }
}
