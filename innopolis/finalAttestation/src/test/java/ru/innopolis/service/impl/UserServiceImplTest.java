package ru.innopolis.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.innopolis.dto.UserDto;
import ru.innopolis.entity.Role;
import ru.innopolis.entity.User;
import ru.innopolis.repository.RoleRepository;
import ru.innopolis.repository.UserRepository;
import ru.innopolis.utils.Constants;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDto testUserDto;
    private User testUser;
    private Role testRole;

    @BeforeEach
    void setUp() {
        testUserDto = new UserDto();
        testUserDto.setUsername("testUser");
        testUserDto.setPassword("password");
        testUserDto.setEmail("test@example.com");

        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testUser");
        testUser.setPassword("encodedPassword");
        testUser.setEmail("test@example.com");
        testUser.setEnabled(true);

        testRole = new Role();
        testRole.setName(Constants.ROLE_USER);
    }

    @Test
    void registerNewUserShouldSaveUser() {
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        when(roleRepository.findByName(eq(Constants.ROLE_USER)))
                .thenReturn(Optional.of(testRole));

        testUser.setRoles(new HashSet<>(Collections.singleton(testRole)));

        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User result = userService.registerNewUser(testUserDto);

        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
        assertEquals("encodedPassword", result.getPassword());
        assertEquals("test@example.com", result.getEmail());
        assertTrue(result.isEnabled());
        assertEquals(1, result.getRoles().size());
        assertTrue(result.getRoles().contains(testRole));

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void registerNewUserShouldThrowExceptionWhenUsernameExists() {
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> userService.registerNewUser(testUserDto));
    }

    @Test
    void registerNewUserShouldThrowExceptionWhenEmailExists() {
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> userService.registerNewUser(testUserDto));
    }

    @Test
    void getUserByIdShouldReturnUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testUser));

        User result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("testUser", result.getUsername());
    }

    @Test
    void getUserByIdShouldThrowExceptionWhenNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.getUserById(1L));
    }

    @Test
    void getUserByUsernameShouldReturnUser() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(testUser));

        User result = userService.getUserByUsername("testUser");

        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
    }

    @Test
    void getUserByUsernameShouldThrowExceptionWhenNotFound() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.getUserByUsername("testUser"));
    }

    @Test
    void existsByUsernameShouldReturnTrue() {
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        boolean result = userService.existsByUsername("testUser");

        assertTrue(result);
    }

    @Test
    void existsByUsernameShouldReturnFalse() {
        when(userRepository.existsByUsername(anyString())).thenReturn(false);

        boolean result = userService.existsByUsername("testUser");

        assertFalse(result);
    }

    @Test
    void existsByEmailShouldReturnTrue() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        boolean result = userService.existsByEmail("test@example.com");

        assertTrue(result);
    }

    @Test
    void existsByEmailShouldReturnFalse() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);

        boolean result = userService.existsByEmail("test@example.com");

        assertFalse(result);
    }
}