package ru.innopolis.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import ru.innopolis.dto.LoginRequestDto;
import ru.innopolis.dto.UserDto;
import ru.innopolis.entity.User;
import ru.innopolis.security.JwtTokenProvider;
import ru.innopolis.service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthController authController;

    @Test
    void loginShouldReturnTokenWhenCredentialsValid() {
        LoginRequestDto request = new LoginRequestDto();
        request.setUsername("user");
        request.setPassword("password");

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any()))
                .thenReturn(authentication);
        when(jwtTokenProvider.generateToken(authentication))
                .thenReturn("test-token");

        var response = authController.login(request);

        // Assert
        assertNotNull(response);
        assertEquals("test-token", response.getBody());
        verify(authenticationManager).authenticate(
                new UsernamePasswordAuthenticationToken("user", "password"));
        verify(jwtTokenProvider).generateToken(authentication);
    }

    @Test
    void registerUserShouldReturnSuccessMessageWhenRegistrationSuccessful() {
        UserDto userDto = new UserDto();
        userDto.setUsername("newuser");
        userDto.setPassword("password");
        userDto.setEmail("new@example.com");

        when(userService.registerNewUser(any(UserDto.class)))
                .thenReturn(mock(User.class));

        var response = authController.registerUser(userDto);

        assertNotNull(response);
        assertEquals("User registered successfully", response.getBody());
        verify(userService).registerNewUser(userDto);
    }

    @Test
    void registerUserShouldReturnErrorMessageWhenRegistrationFails() {
        UserDto userDto = new UserDto();
        userDto.setUsername("existing");
        userDto.setPassword("password");
        userDto.setEmail("existing@example.com");

        when(userService.registerNewUser(any(UserDto.class)))
                .thenThrow(new RuntimeException("Username already exists"));

        var response = authController.registerUser(userDto);

        assertNotNull(response);
        assertEquals("Username already exists", response.getBody());
        verify(userService).registerNewUser(userDto);
    }
}