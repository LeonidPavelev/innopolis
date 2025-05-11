package ru.innopolis.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.innopolis.dto.TaskRequestDto;
import ru.innopolis.dto.TaskResponseDto;
import ru.innopolis.entity.Task;
import ru.innopolis.security.UserPrincipal;
import ru.innopolis.service.impl.TaskServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskApiController {
    private final TaskServiceImpl taskServiceImpl;

    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> getAllTasks(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        List<Task> tasks = taskServiceImpl.getAllTasksForUser(userPrincipal.getId());
        return ResponseEntity.ok(tasks.stream().map(this::convertToResponse).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getTask(@PathVariable Long id) {
        Task task = taskServiceImpl.getTaskById(id);
        return ResponseEntity.ok(convertToResponse(task));
    }

    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(
            @Valid @RequestBody TaskRequestDto request,
            Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Task task = convertToEntity(request);
        Task createdTask = taskServiceImpl.createTask(task, userPrincipal.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToResponse(createdTask));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDto> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequestDto request) {
        Task task = convertToEntity(request);
        Task updatedTask = taskServiceImpl.updateTask(id, task);
        return ResponseEntity.ok(convertToResponse(updatedTask));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteTask(@PathVariable Long id) {
        taskServiceImpl.deleteTask(id);
        return ResponseEntity.ok(Collections.singletonMap("message", "Задача с ID " + id + " успешно удалена"));
    }

    private TaskResponseDto convertToResponse(Task task) {
        return TaskResponseDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .dueDate(task.getDueDate())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }

    private Task convertToEntity(TaskRequestDto request) {
        return Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus())
                .priority(request.getPriority())
                .dueDate(request.getDueDate())
                .build();
    }
}