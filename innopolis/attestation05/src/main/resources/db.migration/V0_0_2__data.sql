INSERT INTO roles (name) VALUES
('ROLE_USER'),
('ROLE_ADMIN')
ON CONFLICT (name) DO NOTHING;

-- Создание администратора (логин: admin, пароль: admin123)
INSERT INTO users (username, password, email, enabled)
VALUES ('admin', '$2a$10$u3H27oqFBoe0shsNx.5HPuGzyB7XA2tI8vJoYAgKwqTdq71hZ4uye', 'admin@example.com', true)
ON CONFLICT (username) DO NOTHING;

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r
WHERE u.username = 'admin' AND r.name = 'ROLE_ADMIN'
ON CONFLICT (user_id, role_id) DO NOTHING;