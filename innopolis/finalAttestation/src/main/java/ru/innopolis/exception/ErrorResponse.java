package ru.innopolis.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO для представления информации об ошибке в API.
 */
@Data
@AllArgsConstructor
@Schema(description = "Стандартный формат ответа при возникновении ошибки")
public class ErrorResponse {
    @Schema(description = "Временная метка возникновения ошибки", example = "2023-01-01T12:00:00")
    private LocalDateTime timestamp;

    @Schema(description = "HTTP статус код ошибки", example = "404")
    private int status;

    @Schema(description = "Тип ошибки", example = "Not Found")
    private String error;

    @Schema(description = "Сообщение об ошибке", example = "Ресурс не найден")
    private String message;

    @Schema(description = "Путь, по которому возникла ошибка", example = "/api/tasks/123")
    private String path;
}