package ru.innopolis.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.innopolis.entity.User;

import java.util.Optional;


/**
 * Репозиторий для работы с пользователями системы.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Находит пользователя по имени.
     *
     * @param username имя пользователя
     * @return Optional с найденным пользователем или пустой
     */
    Optional<User> findByUsername(String username);

    /**
     * Находит пользователя по email.
     *
     * @param email email пользователя
     * @return Optional с найденным пользователем или пустой
     */
    Optional<User> findByEmail(String email);

    /**
     * Проверяет существование пользователя с указанным именем.
     *
     * @param username имя пользователя для проверки
     * @return true если пользователь существует, иначе false
     */
    boolean existsByUsername(String username);

    /**
     * Проверяет существование пользователя с указанным email.
     *
     * @param email email пользователя для проверки
     * @return true если пользователь существует, иначе false
     */
    boolean existsByEmail(String email);
}
