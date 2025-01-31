package ru.innopolis.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.innopolis.entity.UsersEntity;
import ru.innopolis.exceptions.DatabaseOperationException;
import ru.innopolis.exceptions.EntityNotFoundException;
import ru.innopolis.exceptions.InvalidDataException;
import ru.innopolis.repository.UsersRepository;

import java.util.List;
import java.util.stream.Collectors;
@RequiredArgsConstructor
public class UsersRepositoryImpl implements UsersRepository {

    private final JdbcTemplate jdbcTemplate;
    @Override
    public UsersEntity saveUser(UsersEntity user) {
        if (user == null || user.getFirstName() == null || user.getLastName() == null) {
            throw new InvalidDataException("Invalid user data provided");
        }

        String sql = "INSERT INTO online_electronics_store.users (first_name, last_name) VALUES (?, ?) RETURNING user_id";
        try {
            Long userId = jdbcTemplate.queryForObject(sql, Long.class, user.getFirstName(), user.getLastName());
            user.setUserId(userId);
            return user;
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Error saving user", e);
        }
    }

    @Override
    public UsersEntity findById(int id) {
        if (id <= 0) {
            throw new InvalidDataException("User ID must be greater than 0");
        }

        try {
            String sql = "SELECT * FROM online_electronics_store.users WHERE user_id = ?";
            return jdbcTemplate.queryForObject(sql, usersMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("User with id " + id + " not found");
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Error accessing the database", e);
        }
    }

    @Override
    public List<UsersEntity> findAll() {
        try {
            return jdbcTemplate.query("SELECT * FROM online_electronics_store.users", usersMapper);
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Error accessing the database", e);
        }
    }

    @Override
    public void updateUser(UsersEntity user) {
        if (user == null || user.getUserId() == null) {
            throw new InvalidDataException("Invalid user data provided");
        }

        String sql = "UPDATE online_electronics_store.users SET first_name = ?, last_name = ? WHERE user_id = ?";
        try {
            jdbcTemplate.update(sql, user.getFirstName(), user.getLastName(), user.getUserId());
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Error updating user", e);
        }
    }

    @Override
    public List<UsersEntity> findAllSortedByFirstName() {
        try {
            return findAll().stream()
                    .sorted((u1, u2) -> u1.getFirstName().compareToIgnoreCase(u2.getFirstName()))
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Error accessing the database", e);
        }
    }

    @Override
    public List<UsersEntity> findAllSortedByLastName() {
        try {
            return findAll().stream()
                    .sorted((u1, u2) -> u1.getLastName().compareToIgnoreCase(u2.getLastName()))
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Error accessing the database", e);
        }
    }

    @Override
    public void deleteUserById(int userId) {
        if (userId <= 0) {
            throw new InvalidDataException("Invalid user ID");
        }
        try {
            String sql = "DELETE FROM online_electronics_store.users WHERE user_id = ?";
            jdbcTemplate.update(sql, userId);
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Error deleting user", e);
        }
    }

    @Override
    public void deleteAll() {
        try {
            jdbcTemplate.update("DELETE FROM online_electronics_store.users");
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Error deleting all users", e);
        }
    }
    private static final RowMapper<UsersEntity> usersMapper = (row, rowNumber) ->
            UsersEntity.builder()
                    .userId(row.getLong("user_id"))
                    .firstName(row.getString("first_name"))
                    .lastName(row.getString("last_name"))
                    .build();

}
