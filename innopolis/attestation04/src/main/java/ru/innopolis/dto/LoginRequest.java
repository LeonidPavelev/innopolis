package ru.innopolis.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO для запроса аутентификации пользователя.
 */
@Data
@Schema(description = "Данные для входа пользователя")
public class LoginRequest {
    @NotNull
    @Size(min = 3, max = 100)
    @Schema(description = "Имя пользователя", example = "user123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @NotNull
    @Size(min = 3, max = 100)
    @Schema(description = "Пароль пользователя", example = "password123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}
