package ru.innopolis.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.innopolis.dto.UserDto;
import ru.innopolis.entity.User;
import ru.innopolis.service.UserService;
import ru.innopolis.utils.Constants;
/**
 * Контроллер для административных операций с пользователями.
 * Предоставляет эндпоинты для управления ролями пользователей.
 */
@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class AdminUserController {

    private final UserService userService;

    /**
     * Назначает пользователю роль ADMIN.
     *
     * @param userId ID пользователя, которому назначается роль
     * @return ResponseEntity с сообщением о результате операции
     */
    @PostMapping("/{userId}/grant-admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Назначить роль ADMIN", description = "Доступно только для администраторов")
    public ResponseEntity<?> grantAdminRole(
            @Parameter(description = "ID пользователя") @PathVariable Long userId) {
        User user = userService.grantAdminRole(userId);
        UserDto userDto = convertToDto(user);
        return ResponseEntity.ok("Пользователю: " + userDto.getUsername() + ", добавлена роль: " + Constants.ADMIN_ROLE);
    }

    /**
     * Преобразует сущность User в DTO.
     *
     * @param user сущность пользователя
     * @return DTO пользователя
     */
    private UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
