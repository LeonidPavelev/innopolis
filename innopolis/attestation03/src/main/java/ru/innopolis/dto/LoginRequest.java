package ru.innopolis.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {
    @NotNull
    @Size(min = 3, max = 100)
    private String username;
    @NotNull
    @Size(min = 3, max = 100)
    private String password;
}
