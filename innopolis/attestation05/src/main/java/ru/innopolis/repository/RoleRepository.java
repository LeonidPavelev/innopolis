package ru.innopolis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.innopolis.entity.Role;

import java.util.Optional;

/**
 * Репозиторий для работы с ролями пользователей.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * Находит роль по имени.
     *
     * @param name имя роли
     * @return Optional с найденной ролью или пустой, если роль не найдена
     */
    Optional<Role> findByName(String name);
}
