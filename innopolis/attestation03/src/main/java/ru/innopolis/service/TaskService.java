package ru.innopolis.service;

import ru.innopolis.entity.Task;

import java.util.List;

public interface TaskService {
    List<Task> getAllTasksForUser(Long userId);
    Task getTaskById(Long id);
    Task createTask(Task task, Long userId);
    Task updateTask(Long id, Task taskDetails);
    void deleteTask(Long id);
}
