package repository.impl;

import config.JDBCTemplateLink;
import entity.UsersEntity;
import org.springframework.jdbc.core.RowMapper;
import repository.UsersRepository;

import java.sql.Timestamp;
import java.util.List;

public class UsersRepositoryImpl implements UsersRepository {
    @Override
    public List<UsersEntity> findAll() {
        return JDBCTemplateLink.jdbcTemplate().query("SELECT * FROM online_electronics_store.users", usersMapper);
    }

    private static final RowMapper<UsersEntity> usersMapper = (row, rowNumber) ->
            UsersEntity.builder()
                    .userId(row.getInt("user_id"))
                    .firstName(row.getString("first_name"))
                    .lastName(row.getString("last_name"))
                    .createdAt(row.getTimestamp("created_at"))
                    .build();

}
