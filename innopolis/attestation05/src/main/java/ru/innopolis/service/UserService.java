package ru.innopolis.service;

import ru.innopolis.dto.UserDto;
import ru.innopolis.entity.User;
/**
 * Сервис для работы с пользователями.
 */
public interface UserService {
    /**
     * Регистрирует нового пользователя.
     *
     * @param userDto данные пользователя
     * @return зарегистрированный пользователь
     */
    User registerNewUser(UserDto userDto);

    /**
     * Получает пользователя по идентификатору.
     *
     * @param id идентификатор пользователя
     * @return найденный пользователь
     */
    User getUserById(Long id);

    /**
     * Получает пользователя по имени.
     *
     * @param username имя пользователя
     * @return найденный пользователь
     */
    User getUserByUsername(String username);

    /**
     * Проверяет существование пользователя с указанным именем.
     *
     * @param username имя пользователя
     * @return true если пользователь существует, иначе false
     */
    boolean existsByUsername(String username);

    /**
     * Проверяет существование пользователя с указанным email.
     *
     * @param email email пользователя
     * @return true если пользователь существует, иначе false
     */
    boolean existsByEmail(String email);

    /**
     * Назначает пользователю роль администратора (ADMIN).
     *
     * @param userId идентификатор пользователя для назначения прав ADMIN
     * @return обновленный объект пользователя с новой ролью
     * @throws RuntimeException если пользователь или роль ADMIN не найдены
     */
    User grantAdminRole(Long userId);
}
