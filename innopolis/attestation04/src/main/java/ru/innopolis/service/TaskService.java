package ru.innopolis.service;

import ru.innopolis.entity.Task;

import java.util.List;
/**
 * Сервис для работы с задачами пользователей.
 */
public interface TaskService {
    /**
     * Получает все задачи пользователя.
     *
     * @param userId идентификатор пользователя
     * @return список задач
     */
    List<Task> getAllTasksForUser(Long userId);

    /**
     * Получает задачу по идентификатору.
     *
     * @param id идентификатор задачи
     * @return найденная задача
     */
    Task getTaskById(Long id);

    /**
     * Создает новую задачу для пользователя.
     *
     * @param task данные задачи
     * @param userId идентификатор пользователя
     * @return созданная задача
     */
    Task createTask(Task task, Long userId);

    /**
     * Обновляет существующую задачу.
     *
     * @param id идентификатор задачи
     * @param taskDetails новые данные задачи
     * @return обновленная задача
     */
    Task updateTask(Long id, Task taskDetails);

    /**
     * Удаляет задачу.
     *
     * @param id идентификатор задачи
     */
    void deleteTask(Long id);
}
