package ru.innopolis.entity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Сущность пользователя системы.
 */
@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Сущность пользователя системы")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Идентификатор пользователя", example = "1")
    private Long id;

    @Column(nullable = false, unique = true)
    @Schema(description = "Имя пользователя", example = "user123")
    private String username;

    @Column(nullable = false)
    @Schema(description = "Зашифрованный пароль пользователя")
    private String password;

    @Column(nullable = false, unique = true)
    @Schema(description = "Email пользователя", example = "user@example.com")
    private String email;

    @Column(nullable = false)
    @Schema(description = "Флаг активности пользователя", example = "true")
    private boolean enabled = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    @Schema(description = "Дата регистрации пользователя", example = "2023-01-01T00:00:00")
    private LocalDateTime createdAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Schema(description = "Роли пользователя")
    private Set<Role> roles = new HashSet<>();
}
