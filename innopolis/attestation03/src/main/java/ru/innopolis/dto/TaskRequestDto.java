package ru.innopolis.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.innopolis.utils.Priority;
import ru.innopolis.utils.TaskStatus;

import java.time.LocalDateTime;

@Data
public class TaskRequestDto {
    @NotBlank
    @Size(min = 3, max = 100)
    private String title;

    @Size(max = 500)
    private String description;

    @NotNull
    private TaskStatus status;

    @NotNull
    private Priority priority;

    private LocalDateTime dueDate;
}
