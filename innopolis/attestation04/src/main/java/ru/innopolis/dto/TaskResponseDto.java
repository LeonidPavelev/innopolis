package ru.innopolis.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.innopolis.utils.Priority;
import ru.innopolis.utils.TaskStatus;

import java.time.LocalDateTime;

/**
 * DTO для ответа с данными задачи.
 */
@Data
@Builder
@Schema(description = "Данные задачи для ответа")
public class TaskResponseDto {
    @Schema(description = "Идентификатор задачи", example = "1")
    private Long id;

    @Schema(description = "Название задачи", example = "Завершить проект")
    private String title;

    @Schema(description = "Описание задачи", example = "Необходимо завершить все задачи по проекту до конца недели")
    private String description;

    @Schema(description = "Статус задачи", implementation = TaskStatus.class)
    private TaskStatus status;

    @Schema(description = "Приоритет задачи", implementation = Priority.class)
    private Priority priority;

    @Schema(description = "Срок выполнения задачи", example = "2023-12-31T23:59:59")
    private LocalDateTime dueDate;

    @Schema(description = "Дата создания задачи", example = "2023-01-01T00:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "Дата последнего обновления задачи", example = "2023-01-02T12:00:00")
    private LocalDateTime updatedAt;
}
