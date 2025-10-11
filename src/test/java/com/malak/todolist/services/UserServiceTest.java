package com.malak.todolist.services;

import static org.assertj.core.api.Assertions.tuple;

import java.util.List;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.malak.todolist.entities.User;
import com.malak.todolist.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@DataJpaTest
@Import({UserService.class, BCryptPasswordEncoder.class})
public class UserServiceTest {

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Test
    public void userService_getUser_shouldReturnUser(){
        //Arrange
        User user = User.builder().username("malak").email("malak@test.com").password("123").build();
        userRepository.save(user);
        // Act
        User foundedUser = userService.getUser(user.getId());
        // Assert
        Assertions.assertThat(foundedUser).isNotNull();
        Assertions.assertThat(foundedUser.getId()).isEqualTo(user.getId());
        Assertions.assertThat(foundedUser.getUsername()).isEqualTo(user.getUsername());
        Assertions.assertThat(foundedUser.getEmail()).isEqualTo(user.getEmail());

    }

    @Test
    public void userService_getUser_shouldThrowEntityNotFoundException(){
        // Arrange
        UUID id = UUID.randomUUID();
        // Act & Assert
        Assertions.assertThatThrownBy(() -> userService.getUser(id))
            .isInstanceOf(EntityNotFoundException.class)
            .hasMessage("User with " + id + " not found");
    }

    @Test
    public void userService_getAllUsers_shouldReturnListOfUsers(){
        // Arrange
        User user1 = User.builder().username("malak").email("malak@test.com").password("123").build();
        User user2 = User.builder().username("mina").email("mina@test.com").password("12345").build();

        userRepository.save(user1);
        userRepository.save(user2);
        // Act
        List<User> users = userService.getAllUsers();
        // Assert
        Assertions.assertThat(users).hasSize(2)
        .extracting(User::getUsername, User::getEmail)
        .containsExactlyInAnyOrder(
            tuple("malak", "malak@test.com"),
            tuple("mina", "mina@test.com")
        );
    }
    @Test
    public void userService_getAllUsers_shouldReturnEmptyList(){
        // Act
        List<User> users = userService.getAllUsers();
        // Assert
        Assertions.assertThat(users).isEmpty();

    }

    @Test
    public void userService_createUser_shouldReturnUser(){
        // arrange
        User user = User.builder().username("malak").email("malak@test.com").password("123").build();
        // act
        User createdUser = userService.createUser(user);
        // assert
        Assertions.assertThat(createdUser)
        .extracting(User::getUsername, User::getEmail)
        .containsExactly(
            "malak", "malak@test.com"
        );
    }
    @Test
    public void userService_createUser_shouldThrowIllegalArgumentException(){
        // arrange
        User user = null;
        // act & assert
        Assertions.assertThatThrownBy(() -> userService.createUser(user)).isInstanceOf(IllegalArgumentException.class).hasMessage("User cannot be null");
    }

    @Test
    public void userService_deleteUser_shouldThrowEntityNotFoundException(){
        UUID id = UUID.randomUUID();
        Assertions.assertThatThrownBy(() -> userService.deleteUser(id))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage("User with " + id + " not found");
    }

    @Test
    public void userService_deleteUser_shouldDeleteUser(){
        User user = User.builder().username("malak").email("malak@test.com").password("123").build();
        userRepository.save(user);
        userService.deleteUser(user.getId());
        Assertions.assertThat(userRepository.findById(user.getId())).isEmpty();
    }

    @Test
    public void userService_updateUser_updatePasswordWorks(){
        User user = User.builder().username("malak").email("malak@test.com").password("123").build();
        userRepository.save(user);

        UUID id = user.getId();
        // only change the email from com to org
        User updateUser = User.builder().username("malak").email("malak@test.com").password("newPass").build();
        User userAfterUpdate = userService.updateUser(id, updateUser);

        Assertions.assertThat(passwordEncoder.matches("newPass", userAfterUpdate.getPassword())).isTrue();
    }
    @Test
    public void userService_updateUser_throwsException_whenUserNotFound() {
        UUID id = UUID.randomUUID();
        User updatedUser = User.builder().username("a").email("a@test.com").password("123").build();

        Assertions.assertThatThrownBy(() -> userService.updateUser(id, updatedUser))
            .isInstanceOf(EntityNotFoundException.class)
            .hasMessage("User with " + id + " not found");
    }


    
}
