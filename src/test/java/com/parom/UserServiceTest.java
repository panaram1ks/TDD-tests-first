package com.parom;

import com.parom.data.UserRepository;
import com.parom.model.User;
import com.parom.service.EmailVerificationService;
import com.parom.service.EmailVerificationServiceImpl;
import com.parom.service.UserService;
import com.parom.service.UserServiceImpl;
import com.parom.service.exception.EmailNotificationServiceException;
import com.parom.service.exception.UserServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    EmailVerificationServiceImpl emailVerificationService;

    String firstName;
    String lastName;
    String email;
    String password;
    String repeatPassword;

    @BeforeEach
    void init() {
//        this.userService = new UserServiceImpl(userRepository);
        firstName = "Sergey";
        lastName = "Ivanov";
        email = "test@test.com";
        password = "12345678";
        repeatPassword = "12345678";
    }

    @DisplayName("User object creator")
    @Test
    void testCreateUser_whenUserDetailsProvided_returnUserObject() {
        // Arrange
        Mockito.when(userRepository.save(any(User.class))).thenReturn(true);

        // Act
        User user = userService.createUser(firstName, lastName, email, password, repeatPassword);

        // Assert
        assertNotNull(user, "create User method should not return null");
        assertEquals(firstName, user.getFirstName(), "user's first name is incorrect");
        assertEquals(lastName, user.getLastName(), "user's last name is incorrect");
        assertEquals(email, user.getEmail(), "user's email is incorrect");
        assertNotNull(user.getId(), "User id is missing");

        Mockito.verify(userRepository, Mockito.times(1)).save(any(User.class));
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

    @DisplayName("If save() method cause RuntimeException, a UserServiceException is thrown")
    @Test
    void testCreateUser_whenSaveMethodThrowsException_thenThrowsUserServiceException() {
        // Arrange
        Mockito.when(userRepository.save(any(User.class))).thenThrow(RuntimeException.class);
        // Act & Assert
        assertThrows(
                UserServiceException.class,
                () -> userService.createUser(firstName, lastName, email, password, repeatPassword),
                "Should have thrown UserServiceException instead"
        );
        // Assert
    }

    @DisplayName("EmailNotificationException is handle")
    @Test
    void testCreateUser_whenEmailConfirmation_thenThrowsUserServiceException() {
        Mockito.doThrow(RuntimeException.class).when(emailVerificationService).scheduleEmailConfirmation(any(User.class)); // for void methods
//        Mockito.doNothing().when(emailVerificationService).scheduleEmailConfirmation(Mockito.any(User.class));
        Mockito.when(userRepository.save(any(User.class))).thenReturn(true);

        assertThrows(
                EmailNotificationServiceException.class,
                () -> userService.createUser(firstName, lastName, email, password, repeatPassword),
                "Should have thrown UserServiceException instead"
        );
        Mockito.verify(emailVerificationService, times(1)).scheduleEmailConfirmation(any(User.class));
    }

    @DisplayName("Schedule Email Confirmation is executed")
    @Test
    void testCreateUser_whenUserCreated_scheduleEmailConfirmation() {
        when(userRepository.save(any(User.class))).thenReturn(true);
        doCallRealMethod().when(emailVerificationService).scheduleEmailConfirmation(any(User.class));

        userService.createUser(firstName, lastName, email, password, repeatPassword);
        verify(emailVerificationService, times(1)).scheduleEmailConfirmation(any(User.class));
    }


}
