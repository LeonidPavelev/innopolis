package ru.innopolis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.innopolis.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
