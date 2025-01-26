package ru.innopolis.repository.impl;

import ru.innopolis.config.JDBCTemplateConfig;
import ru.innopolis.entity.UsersEntity;
import org.springframework.jdbc.core.RowMapper;
import ru.innopolis.repository.UsersRepository;

import java.util.List;

public class UsersRepositoryImpl implements UsersRepository {
    @Override
    public UsersEntity saveUser(UsersEntity user) {
        String sql = "INSERT INTO online_electronics_store.users (first_name, last_name) VALUES (?, ?) RETURNING user_id";
        Long userId = JDBCTemplateConfig.jdbcTemplate().queryForObject(sql, new Object[]{user.getFirstName(), user.getLastName()}, Long.class);
        user.setUserId(userId);
        return user;
    }

    @Override
    public UsersEntity findById(int id) {
        String sql = "SELECT * FROM online_electronics_store.users WHERE user_id = ?";
        return JDBCTemplateConfig.jdbcTemplate().queryForObject(sql, new Object[]{id}, usersMapper);
    }

    @Override
    public List<UsersEntity> findAll() {
        return JDBCTemplateConfig.jdbcTemplate().query("SELECT * FROM online_electronics_store.users", usersMapper);
    }

    @Override
    public void updateUser(UsersEntity user) {
        String sql = "UPDATE online_electronics_store.users SET first_name = ?, last_name = ? WHERE user_id = ?";
        JDBCTemplateConfig.jdbcTemplate().update(sql, user.getFirstName(), user.getLastName(), user.getUserId());
    }

    @Override
    public void deleteUserById(int userId) {
        String sql = "DELETE FROM online_electronics_store.users WHERE user_id = ?";
        JDBCTemplateConfig.jdbcTemplate().update(sql, userId);
    }

    @Override
    public void deleteAll() {
        JDBCTemplateConfig.jdbcTemplate().update("DELETE FROM online_electronics_store.users");
    }

    private static final RowMapper<UsersEntity> usersMapper = (row, rowNumber) ->
            UsersEntity.builder()
                    .userId(row.getLong("user_id"))
                    .firstName(row.getString("first_name"))
                    .lastName(row.getString("last_name"))
                    .build();

}
