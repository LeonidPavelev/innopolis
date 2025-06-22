package ru.innopolis.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * DTO для регистрации пользователя.
 */
@Data
@Schema(description = "Данные для регистрации пользователя")
public class UserDto {
    @NotBlank
    @Size(min = 3, max = 50)
    @Schema(description = "Имя пользователя", example = "user123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @NotBlank
    @Size(min = 6, max = 100)
    @Schema(description = "Пароль пользователя", example = "password123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @NotBlank
    @Email
    @Schema(description = "Email пользователя", example = "user@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
}