package ru.innopolis.repository.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.innopolis.entity.UsersEntity;
import ru.innopolis.exceptions.DatabaseOperationException;
import ru.innopolis.exceptions.EntityNotFoundException;
import ru.innopolis.exceptions.InvalidDataException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class UsersRepositoryImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private UsersRepositoryImpl usersRepository;

    @Test
    void saveUserPositive() {
        UsersEntity user = UsersEntity.builder()
                .firstName("Андрей")
                .lastName("Жаров")
                .build();

        when(jdbcTemplate.queryForObject(anyString(), eq(Long.class), any(Object[].class))).thenReturn(1L);

        UsersEntity savedUser = usersRepository.saveUser(user);

        assertNotNull(savedUser);
        assertEquals(1L, savedUser.getUserId());
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), eq(Long.class), any(Object[].class));
    }

    @Test
    void saveUserNegativeInvalidData() {
        UsersEntity user = UsersEntity.builder()
                .firstName(null)
                .lastName("Жаров")
                .build();

        assertThrows(InvalidDataException.class, () -> usersRepository.saveUser(user));
    }

    @Test
    void saveUserNegativeDatabaseError() {
        UsersEntity user = UsersEntity.builder()
                .firstName("Андрей")
                .lastName("Жаров")
                .build();

        when(jdbcTemplate.queryForObject(anyString(), eq(Long.class), any(Object[].class)))
                .thenThrow(new DataAccessException("Database error") {});

        assertThrows(DatabaseOperationException.class, () -> usersRepository.saveUser(user));
    }
    @Test
    void findByIdPositive() {
        UsersEntity user = UsersEntity.builder()
                .userId(1L)
                .firstName("Андрей")
                .lastName("Жаров")
                .build();

        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), any(Object[].class))).thenReturn(user);

        UsersEntity foundUser = usersRepository.findById(1);

        assertNotNull(foundUser);
        assertEquals(1L, foundUser.getUserId());
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), any(RowMapper.class), any(Object[].class));
    }

    @Test
    void findByIdNegativeInvalidId() {
        assertThrows(InvalidDataException.class, () -> usersRepository.findById(0));
    }

    @Test
    void findByIdNegativeUserNotFound() {
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), any(Object[].class)))
                .thenThrow(new EmptyResultDataAccessException(1));

        assertThrows(EntityNotFoundException.class, () -> usersRepository.findById(1));
    }

    @Test
    void findByIdNegativeDatabaseError() {
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), any(Object[].class)))
                .thenThrow(new DataAccessException("Database error") {});

        assertThrows(DatabaseOperationException.class, () -> usersRepository.findById(1));
    }
    @Test
    void findAllPositive() {
        UsersEntity user = UsersEntity.builder()
                .firstName("Андрей")
                .lastName("Жаров")
                .build();

        UsersEntity user2 = UsersEntity.builder()
                .firstName("Николай")
                .lastName("Быстров")
                .build();

        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(List.of(user, user2));

        List<UsersEntity> users = usersRepository.findAll();

        assertNotNull(users);
        assertEquals(2, users.size());
        verify(jdbcTemplate, times(1)).query(anyString(), any(RowMapper.class));
    }

    @Test
    void findAllNegativeDatabaseError() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenThrow(new DataAccessException("Database error") {});

        assertThrows(DatabaseOperationException.class, () -> usersRepository.findAll());
    }

    @Test
    void updateUserNegativeDatabaseError() {
        UsersEntity user = UsersEntity.builder()
                .userId(1L)
                .firstName("Андрей")
                .lastName("Жаров")
                .build();

        doThrow(new DataAccessException("Database error") {}).when(jdbcTemplate).update(anyString(), any(), any(), any());

        assertThrows(DatabaseOperationException.class, () -> usersRepository.updateUser(user));
    }
    @Test
    void findAllSortedByFirstNamePositive() {
        UsersEntity user = UsersEntity.builder()
                .userId(1L)
                .firstName("Андрей")
                .lastName("Жаров")
                .build();

        UsersEntity user2 = UsersEntity.builder()
                .userId(2L)
                .firstName("Николай")
                .lastName("Быстров")
                .build();

        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(List.of(user, user2));

        List<UsersEntity> users = usersRepository.findAllSortedByFirstName();

        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals("Андрей", users.get(0).getFirstName());
        assertEquals("Николай", users.get(1).getFirstName());
        verify(jdbcTemplate, times(1)).query(anyString(), any(RowMapper.class));
    }

    @Test
    void findAllSortedByFirstNameNegativeDatabaseError() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenThrow(new DataAccessException("Database error") {});

        assertThrows(DatabaseOperationException.class, () -> usersRepository.findAllSortedByFirstName());
    }
    @Test
    void findAllSortedByLastNamePositive() {
        UsersEntity user = UsersEntity.builder()
                .userId(1L)
                .firstName("Андрей")
                .lastName("Жаров")
                .build();

        UsersEntity user2 = UsersEntity.builder()
                .userId(2L)
                .firstName("Николай")
                .lastName("Быстров")
                .build();

        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(List.of(user, user2));

        List<UsersEntity> users = usersRepository.findAllSortedByLastName();

        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals("Быстров", users.get(0).getLastName());
        assertEquals("Жаров", users.get(1).getLastName());
        verify(jdbcTemplate, times(1)).query(anyString(), any(RowMapper.class));
    }

    @Test
    void findAllSortedByLastNameNegativeDatabaseError() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenThrow(new DataAccessException("Database error") {});

        assertThrows(DatabaseOperationException.class, () -> usersRepository.findAllSortedByLastName());
    }

    @Test
    void deleteUserByIdNegativeInvalidId() {
        assertThrows(InvalidDataException.class, () -> usersRepository.deleteUserById(0));
    }

    @Test
    void deleteAllNegativeDatabaseError() {
        doThrow(new DataAccessException("Database error") {}).when(jdbcTemplate).update(anyString());

        assertThrows(DatabaseOperationException.class, () -> usersRepository.deleteAll());
    }
}