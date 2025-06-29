package ru.innopolis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.innopolis.entity.Task;

import java.util.List;
import java.util.Optional;
/**
 * Репозиторий для работы с задачами пользователей.
 */
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Находит все неудаленные задачи пользователя.
     *
     * @param userId идентификатор пользователя
     * @return список задач пользователя
     */
    List<Task> findByUserIdAndDeletedFalse(Long userId);

    /**
     * Находит неудаленную задачу по идентификатору.
     *
     * @param id идентификатор задачи
     * @return Optional с найденной задачей или пустой, если задача не найдена или удалена
     */
    Optional<Task> findByIdAndUserIdAndDeletedFalse(Long id, Long userId);

    /**
     * Находит все неудаленные задачи.
     *
     * @return список всех неудаленных задач
     */
    @Query("SELECT t FROM Task t WHERE t.deleted = false")
    List<Task> findAllNotDeleted();

    /**
     * Выполняет мягкое удаление задачи.
     *
     * @param id идентификатор задачи для удаления
     */
    @Query("UPDATE Task t SET t.deleted = true WHERE t.id = :id")
    @Modifying
    void softDelete(@Param("id") Long id);
}
