package ru.innopolis.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.innopolis.entity.Task;
import ru.innopolis.entity.User;
import ru.innopolis.exception.ResourceNotFoundException;
import ru.innopolis.repository.TaskRepository;
import ru.innopolis.service.TaskService;
import ru.innopolis.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;

    @Override
    public List<Task> getAllTasksForUser(Long userId) {
        return taskRepository.findByUserIdAndDeletedFalse(userId);
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
    }

    @Override
    @Transactional
    public Task createTask(Task task, Long userId) {
        User user = userService.getUserById(userId);
        task.setUser(user);
        return taskRepository.save(task);
    }

    @Override
    @Transactional
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
    public void deleteTask(Long id) {
        taskRepository.softDelete(id);
    }
}
