package ru.innopolis.controller;

import org.springframework.web.bind.annotation.*;
import ru.innopolis.model.Task;
import ru.innopolis.repository.TaskRepository;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskApiController {
    private final TaskRepository taskRepository;

    public TaskApiController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskRepository.save(task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
    }
}
