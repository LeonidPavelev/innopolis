package ru.innopolis.dto;

import lombok.Builder;
import lombok.Data;
import ru.innopolis.utils.Priority;
import ru.innopolis.utils.TaskStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class TaskResponseDto {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private Priority priority;
    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
