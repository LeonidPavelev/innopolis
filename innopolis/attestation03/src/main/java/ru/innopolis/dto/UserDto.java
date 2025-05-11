package ru.innopolis.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserDto {
    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank
    @Size(min = 6, max = 100)
    private String password;

    @NotBlank
    @Email
    private String email;
}