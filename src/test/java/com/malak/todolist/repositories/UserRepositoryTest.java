package com.malak.todolist.repositories;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.malak.todolist.entities.User;

@DataJpaTest
public class UserRepositoryTest {
    
    @Autowired
    private UserRepository userRepository;

    @Test
    public void UserRepository_findById_returnsUser() {
        // Arrange
        User user = User.builder().username("test").email("test@gmail.com").password("123").build();
        User savedUser = userRepository.save(user);
        // Act
        User foundUser = userRepository.findById(savedUser.getId()).orElse(null);
        // Assert
        Assertions.assertThat(foundUser).isNotNull();
    }
}
