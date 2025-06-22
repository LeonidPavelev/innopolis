package ru.innopolis.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.innopolis.utils.Priority;
import ru.innopolis.utils.TaskStatus;

import java.time.LocalDateTime;

/**
 * Сущность задачи пользователя.
 */
@Entity
@Table(name = "tasks")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Сущность задачи пользователя")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Идентификатор задачи", example = "1")
    private Long id;

    @Column(nullable = false, length = 100)
    @Schema(description = "Название задачи", example = "Завершить проект")
    private String title;

    @Column(columnDefinition = "TEXT")
    @Schema(description = "Описание задачи", example = "Необходимо завершить все задачи по проекту до конца недели")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Статус задачи", implementation = TaskStatus.class)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Приоритет задачи", implementation = Priority.class)
    private Priority priority;

    @Column(name = "due_date")
    @Schema(description = "Срок выполнения задачи", example = "2023-12-31T23:59:59")
    private LocalDateTime dueDate;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    @Schema(description = "Дата создания задачи", example = "2023-01-01T00:00:00")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Schema(description = "Дата последнего обновления задачи", example = "2023-01-02T12:00:00")
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    @Schema(description = "Флаг удаления задачи", example = "false")
    private boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @Schema(description = "Пользователь, которому принадлежит задача")
    private User user;
}