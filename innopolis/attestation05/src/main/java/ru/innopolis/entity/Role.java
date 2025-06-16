package ru.innopolis.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

/**
 * Сущность роли пользователя.
 */
@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Сущность роли пользователя")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Идентификатор роли", example = "1")
    private Long id;

    @Column(nullable = false, unique = true)
    @Schema(description = "Название роли", example = "ROLE_USER")
    private String name;
}
