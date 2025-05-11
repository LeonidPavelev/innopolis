package ru.innopolis.service;

import ru.innopolis.dto.UserDto;
import ru.innopolis.entity.User;

public interface UserService {
    User registerNewUser(UserDto userDto);
    User getUserById(Long id);
    User getUserByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
