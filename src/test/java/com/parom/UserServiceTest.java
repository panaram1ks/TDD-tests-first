package com.parom;

import com.parom.model.User;
import com.parom.service.UserService;
import com.parom.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    UserService userService;
    String firstName;
    String lastName;
    String email;
    String password;
    String repeatPassword;

    @BeforeEach
    void init() {
        this.userService = new UserServiceImpl();
        firstName = "Sergey";
        lastName = "Ivanov";
        email = "test@test.com";
        password = "12345678";
        repeatPassword = "12345678";
    }

    @DisplayName("User object creator")
    @Test
    void testCreateUser_whenUserDetailsProvided_returnUserObject() {
        // Act
        User user = userService.createUser(firstName, lastName, email, password, repeatPassword);

        // Assert
        assertNotNull(user, "create User method should not return null");
        assertEquals(firstName, user.getFirstName(), "user's first name is incorrect");
        assertEquals(lastName, user.getLastName(), "user's last name is incorrect");
        assertEquals(email, user.getEmail(), "user's email is incorrect");
        assertNotNull(user.getId(), "User id is missing");
    }

    @DisplayName("Empty first name causes correct exception")
    @Test
    void testCreateUser_whenFirstNameIsEmpty_throwsIllegalArgumentException() {
        // Arrange
        firstName = "";
        // Assert & Act
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class,
                () -> userService.createUser(firstName, lastName, email, password, repeatPassword),
                "Empty first name should cause IllegalArgumentException");
        String message = illegalArgumentException.getMessage();
        assertEquals("Users first name is empty", message, "Exception message is not correct");
    }


}
