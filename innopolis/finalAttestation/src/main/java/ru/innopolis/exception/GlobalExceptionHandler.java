package ru.innopolis.exception;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.innopolis.integration.kafka.ErrorNotificationService;

import java.time.LocalDateTime;

import static ru.innopolis.utils.ExceptionUtils.*;

/**
 * Глобальный обработчик исключений для REST API.
 */
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final ErrorNotificationService errorNotificationService;

    /**
     * Обрабатывает исключения ResourceNotFoundException.
     *
     * @param ex исключение
     * @param request HTTP запрос
     * @return ResponseEntity с информацией об ошибке
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @Operation(hidden = true)
    @ApiResponse(responseCode = "404", description = "Ресурс не найден")
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            ResourceNotFoundException ex, HttpServletRequest request) {

        String path = request.getRequestURI();
        errorNotificationService.sendErrorNotification(ex.getMessage(), path, getStackTrace(ex));

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getRequestURI());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Обрабатывает все необработанные исключения.
     *
     * @param ex исключение
     * @param request HTTP запрос
     * @return ResponseEntity с информацией об ошибке
     */
    @ExceptionHandler(Exception.class)
    @Operation(hidden = true)
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    public ResponseEntity<ErrorResponse> handleGlobalException(
            Exception ex, HttpServletRequest request) {

        String path = request.getRequestURI();
        errorNotificationService.sendErrorNotification(ex.getMessage(), path, getStackTrace(ex));

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                ex.getMessage(),
                request.getRequestURI());

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}