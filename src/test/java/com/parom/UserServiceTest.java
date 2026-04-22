package com.parom;

import com.parom.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserServiceTest {

    @Test
    void testCreateUser_whenUserDetailsProvided_returnUserObject() {
        // Arrange
        UserService userService = new UserServiceImpl();
        String firstName = "Sergey";
        String lastName = "Ivanov";
        String email = "test@test.com";
        String password = "12345678";
        String repeatPassword = "12345678";

        // Act
       User user = userService.createUser(firstName, lastName, email, password, repeatPassword);

       // Assert
        assertNotNull(user, "create User method should not return null");
    }

}
