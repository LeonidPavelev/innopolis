package ru.innopolis.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.innopolis.entity.Task;
import ru.innopolis.entity.User;
import ru.innopolis.exception.ResourceNotFoundException;
import ru.innopolis.repository.TaskRepository;
import ru.innopolis.service.TaskService;
import ru.innopolis.service.UserService;

import java.util.List;
/**
 * Реализация сервиса для работы с задачами пользователей.
 */
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;

    @Override
    @Cacheable("tasks")
    public List<Task> getAllTasksForUser(Long userId) {
        return taskRepository.findByUserIdAndDeletedFalse(userId);
    }

    @Override
    @Cacheable(value = "tasks", key = "#id")
    public Task getTaskById(Long id) {
        return taskRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
    }

    @Override
    @Transactional
    @CacheEvict(value = "tasks", key = "#userId")
    public Task createTask(Task task, Long userId) {
        User user = userService.getUserById(userId);
        task.setUser(user);
        return taskRepository.save(task);
    }

    @Override
    @Transactional
    @CachePut(value = "tasks", key = "#id")
    public Task updateTask(Long id, Task taskDetails) {
        Task task = getTaskById(id);
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setStatus(taskDetails.getStatus());
        task.setPriority(taskDetails.getPriority());
        task.setDueDate(taskDetails.getDueDate());
        return taskRepository.save(task);
    }

    @Override
    @Transactional
    @CacheEvict(value = "tasks", key = "#id")
    public void deleteTask(Long id) {
        taskRepository.softDelete(id);
    }
}
