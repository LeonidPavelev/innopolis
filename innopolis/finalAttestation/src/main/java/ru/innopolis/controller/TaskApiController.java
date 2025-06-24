package ru.innopolis.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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

/**
 * Контроллер для управления задачами пользователей.
 */
@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Tag(name = "Задачи", description = "API для управления задачами пользователей")
@SecurityRequirement(name = "bearerAuth")
public class TaskApiController {
    private final TaskServiceImpl taskServiceImpl;

    /**
     * Получает список всех задач текущего пользователя.
     *
     * @param authentication данные аутентификации пользователя
     * @return список задач пользователя
     */
    @Operation(summary = "Получить все задачи", description = "Возвращает все задачи текущего пользователя")
    @ApiResponse(responseCode = "200", description = "Список задач успешно получен",
            content = @Content(schema = @Schema(implementation = TaskResponseDto.class)))
    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> getAllTasks(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        List<Task> tasks = taskServiceImpl.getAllTasksForUser(userPrincipal.getId());
        return ResponseEntity.ok(tasks.stream().map(this::convertToResponse).collect(Collectors.toList()));
    }

    /**
     * Получает задачу по её идентификатору.
     *
     * @param id идентификатор задачи
     * @return данные задачи
     */
    @Operation(summary = "Получить задачу по ID", description = "Возвращает задачу по указанному идентификатору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача найдена",
                    content = @Content(schema = @Schema(implementation = TaskResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getTask(
            @Parameter(description = "Идентификатор задачи", required = true)
            @PathVariable Long id,
            Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Task task = taskServiceImpl.getTaskById(id, userPrincipal.getId());
        return ResponseEntity.ok(convertToResponse(task));
    }

    /**
     * Создает новую задачу для текущего пользователя.
     *
     * @param request данные для создания задачи
     * @param authentication данные аутентификации пользователя
     * @return созданная задача
     */
    @Operation(summary = "Создать задачу", description = "Создает новую задачу для текущего пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Задача успешно создана",
                    content = @Content(schema = @Schema(implementation = TaskResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные задачи")
    })
    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(
            @Valid @RequestBody TaskRequestDto request,
            Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Task task = convertToEntity(request);
        Task createdTask = taskServiceImpl.createTask(task, userPrincipal.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToResponse(createdTask));
    }

    /**
     * Обновляет существующую задачу.
     *
     * @param id идентификатор задачи
     * @param request новые данные задачи
     * @return обновленная задача
     */
    @Operation(summary = "Обновить задачу", description = "Обновляет существующую задачу по идентификатору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача успешно обновлена",
                    content = @Content(schema = @Schema(implementation = TaskResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные задачи"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDto> updateTask(
            @Parameter(description = "Идентификатор задачи", required = true)
            @PathVariable Long id,
            @Valid @RequestBody TaskRequestDto request,
            Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Task task = convertToEntity(request);
        Task updatedTask = taskServiceImpl.updateTask(id, task, userPrincipal.getId());
        return ResponseEntity.ok(convertToResponse(updatedTask));
    }

    /**
     * Удаляет задачу по её идентификатору.
     *
     * @param id идентификатор задачи
     * @return сообщение об успешном удалении
     */
    @Operation(summary = "Удалить задачу", description = "Удаляет задачу по указанному идентификатору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteTask(
            @Parameter(description = "Идентификатор задачи", required = true)
            @PathVariable Long id,
            Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        taskServiceImpl.deleteTask(id, userPrincipal.getId());
        return ResponseEntity.ok(Collections.singletonMap("message", "Задача с ID " + id + " успешно удалена"));
    }

    /**
     * Преобразует сущность Task в DTO для ответа.
     */
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

    /**
     * Преобразует DTO запроса в сущность Task.
     */
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