package ru.innopolis.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.innopolis.dto.UserDto;
import ru.innopolis.entity.Role;
import ru.innopolis.entity.User;
import ru.innopolis.repository.RoleRepository;
import ru.innopolis.repository.UserRepository;
import ru.innopolis.integration.kafka.ErrorNotificationService;
import ru.innopolis.service.UserService;
import ru.innopolis.utils.Constants;

import java.util.Collections;

import static ru.innopolis.utils.ExceptionUtils.getStackTrace;

/**
 * Реализация сервиса для работы с пользователями.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ErrorNotificationService errorNotificationService; ;

    @Override
    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public User registerNewUser(UserDto userDto) {
        if (existsByUsername(userDto.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setEnabled(true);

        Role userRole = roleRepository.findByName(Constants.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRoles(Collections.singleton(userRole));

        return userRepository.save(user);
    }

    @Override
    @Cacheable("users")
    public User getUserById(Long id) {
        try {
            return userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found"));
        } catch (RuntimeException ex) {
            errorNotificationService.sendErrorNotification(
                    ex.getMessage(),
                    "/users/" + id,
                    getStackTrace(ex)
            );
            throw ex;
        }
    }

    @Override
    @Cacheable(value = "users", key = "#username")
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    @Transactional
    public User grantAdminRole(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Role adminRole = roleRepository.findByName(Constants.ADMIN_ROLE)
                .orElseThrow(() -> new RuntimeException("Role ROLE_ADMIN not found"));

        user.getRoles().add(adminRole);
        return userRepository.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

}
