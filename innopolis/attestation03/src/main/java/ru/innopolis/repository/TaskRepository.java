package ru.innopolis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.innopolis.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUserIdAndDeletedFalse(Long userId);

    Optional<Task> findByIdAndDeletedFalse(Long id);

    @Query("SELECT t FROM Task t WHERE t.deleted = false")
    List<Task> findAllNotDeleted();

    @Query("UPDATE Task t SET t.deleted = true WHERE t.id = :id")
    @Modifying
    void softDelete(@Param("id") Long id);
}
