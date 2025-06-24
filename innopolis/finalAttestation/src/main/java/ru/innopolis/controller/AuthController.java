package ru.innopolis.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.innopolis.dto.LoginRequestDto;
import ru.innopolis.dto.UserDto;
import ru.innopolis.security.JwtTokenProvider;
import ru.innopolis.service.UserService;
/**
 * Контроллер для обработки запросов аутентификации и регистрации пользователей.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация", description = "API для входа и регистрации пользователей")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Аутентифицирует пользователя и возвращает JWT токен.
     *
     * @param request данные для входа (логин и пароль)
     * @return JWT токен в случае успешной аутентификации
     */
    @Operation(summary = "Аутентификация пользователя", description = "Проверяет учетные данные и возвращает JWT токен")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешная аутентификация",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "Неверные учетные данные")
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(token);
    }

    /**
     * Регистрирует нового пользователя в системе.
     *
     * @param userDto данные нового пользователя
     * @return сообщение о результате регистрации
     */
    @Operation(summary = "Регистрация пользователя", description = "Создает новую учетную запись пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно зарегистрирован"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные пользователя")
    })
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@ModelAttribute UserDto userDto) {
        try {
            userService.registerNewUser(userDto);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
