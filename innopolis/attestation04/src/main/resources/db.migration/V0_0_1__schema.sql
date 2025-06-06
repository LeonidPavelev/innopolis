CREATE SCHEMA IF NOT EXISTS task_manager;

CREATE TABLE IF NOT EXISTS users (
id BIGSERIAL PRIMARY KEY,
username VARCHAR(50) NOT NULL UNIQUE,
password VARCHAR(100) NOT NULL,
email VARCHAR(100) NOT NULL UNIQUE,
enabled BOOLEAN NOT NULL DEFAULT TRUE,
created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE users IS 'Таблица пользователей системы';
COMMENT ON COLUMN users.id IS 'Уникальный идентификатор пользователя';
COMMENT ON COLUMN users.username IS 'Логин пользователя (уникальный)';
COMMENT ON COLUMN users.password IS 'Зашифрованный пароль пользователя';
COMMENT ON COLUMN users.email IS 'Email пользователя (уникальный)';
COMMENT ON COLUMN users.enabled IS 'Флаг активности аккаунта (true - активен)';
COMMENT ON COLUMN users.created_at IS 'Дата и время создания пользователя';

CREATE TABLE IF NOT EXISTS roles (
id BIGSERIAL PRIMARY KEY,
name VARCHAR(50) NOT NULL UNIQUE
);

COMMENT ON TABLE roles IS 'Таблица ролей пользователей';
COMMENT ON COLUMN roles.id IS 'Уникальный идентификатор роли';
COMMENT ON COLUMN roles.name IS 'Название роли (например, ROLE_USER, ROLE_ADMIN)';

CREATE TABLE IF NOT EXISTS user_roles (
user_id BIGINT NOT NULL,
role_id BIGINT NOT NULL,
PRIMARY KEY (user_id, role_id),
CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

COMMENT ON TABLE user_roles IS 'Таблица связи пользователей и ролей (многие-ко-многим)';
COMMENT ON COLUMN user_roles.user_id IS 'ID пользователя';
COMMENT ON COLUMN user_roles.role_id IS 'ID роли';

CREATE TABLE IF NOT EXISTS tasks
(
id          BIGSERIAL PRIMARY KEY,
title       VARCHAR(100) NOT NULL,
description TEXT,
status      VARCHAR(20)  NOT NULL,
priority    VARCHAR(20)  NOT NULL,
due_date    TIMESTAMP WITH TIME ZONE,
created_at  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
updated_at  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
deleted     BOOLEAN                  DEFAULT FALSE,
user_id     BIGINT       NOT NULL,
CONSTRAINT fk_user_task FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

COMMENT ON TABLE tasks IS 'Таблица задач пользователей';
COMMENT ON COLUMN tasks.id IS 'Уникальный идентификатор задачи';
COMMENT ON COLUMN tasks.title IS 'Заголовок задачи';
COMMENT ON COLUMN tasks.description IS 'Подробное описание задачи';
COMMENT ON COLUMN tasks.status IS 'Статус задачи (NEW, IN_PROGRESS, COMPLETED)';
COMMENT ON COLUMN tasks.priority IS 'Приоритет задачи (LOW, MEDIUM, HIGH)';
COMMENT ON COLUMN tasks.due_date IS 'Срок выполнения задачи';
COMMENT ON COLUMN tasks.created_at IS 'Дата создания задачи';
COMMENT ON COLUMN tasks.updated_at IS 'Дата последнего обновления задачи';
COMMENT ON COLUMN tasks.deleted IS 'Флаг мягкого удаления (true - удалена)';
COMMENT ON COLUMN tasks.user_id IS 'ID пользователя-владельца задачи';

CREATE INDEX IF NOT EXISTS idx_tasks_user_id ON tasks(user_id);
CREATE INDEX IF NOT EXISTS idx_tasks_status ON tasks(status);
CREATE INDEX IF NOT EXISTS idx_tasks_priority ON tasks(priority);
CREATE INDEX IF NOT EXISTS idx_tasks_deleted ON tasks(deleted);