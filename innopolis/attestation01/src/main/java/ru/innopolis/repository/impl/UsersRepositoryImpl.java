package ru.innopolis.repository.impl;

import ru.innopolis.config.JDBCTemplateConfig;
import ru.innopolis.entity.UsersEntity;
import org.springframework.jdbc.core.RowMapper;
import ru.innopolis.repository.UsersRepository;

import java.util.List;

public class UsersRepositoryImpl implements UsersRepository {
    @Override
    public List<UsersEntity> findAll() {
        return JDBCTemplateConfig.jdbcTemplate().query("SELECT * FROM online_electronics_store.users", usersMapper);
    }

    private static final RowMapper<UsersEntity> usersMapper = (row, rowNumber) ->
            UsersEntity.builder()
                    .userId(row.getLong("user_id"))
                    .firstName(row.getString("first_name"))
                    .lastName(row.getString("last_name"))
                    .createdAt(row.getTimestamp("created_at"))
                    .build();

}
