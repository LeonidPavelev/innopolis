package ru.innopolis.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.innopolis.utils.Priority;
import ru.innopolis.utils.TaskStatus;

import java.time.LocalDateTime;

/**
 * DTO для запроса создания/обновления задачи.
 */
@Data
@Schema(description = "Данные для создания или обновления задачи")
public class TaskRequestDto {
    @NotBlank
    @Size(min = 3, max = 100)
    @Schema(description = "Название задачи", example = "Завершить проект", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @Size(max = 500)
    @Schema(description = "Описание задачи", example = "Необходимо завершить все задачи по проекту до конца недели")
    private String description;

    @NotNull
    @Schema(description = "Статус задачи", implementation = TaskStatus.class, requiredMode = Schema.RequiredMode.REQUIRED)
    private TaskStatus status;

    @NotNull
    @Schema(description = "Приоритет задачи", implementation = Priority.class, requiredMode = Schema.RequiredMode.REQUIRED)
    private Priority priority;

    @Schema(description = "Срок выполнения задачи", example = "2023-12-31T23:59:59")
    private LocalDateTime dueDate;
}
