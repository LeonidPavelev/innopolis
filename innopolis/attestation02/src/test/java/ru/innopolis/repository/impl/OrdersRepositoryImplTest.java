package ru.innopolis.repository.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.innopolis.entity.OrdersEntity;
import ru.innopolis.exceptions.DatabaseOperationException;
import ru.innopolis.exceptions.EntityNotFoundException;
import ru.innopolis.exceptions.InvalidDataException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class OrdersRepositoryImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private OrdersRepositoryImpl ordersRepository;

    @Test
    void saveOrderPositive() {
        OrdersEntity order = OrdersEntity.builder()
                .userId(1L)
                .totalAmount(145.76)
                .build();

        when(jdbcTemplate.queryForObject(anyString(), eq(Long.class), any(Object[].class))).thenReturn(1L);

        OrdersEntity savedOrder = ordersRepository.saveOrder(order);

        assertNotNull(savedOrder);
        assertEquals(1L, savedOrder.getOrderId());
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), eq(Long.class), any(Object[].class));
    }

    @Test
    void saveOrderNegativeInvalidData() {
        OrdersEntity order = OrdersEntity.builder()
                .userId(1L)
                .totalAmount(null)
                .build();

        assertThrows(NullPointerException.class, () -> ordersRepository.saveOrder(order));
    }

    @Test
    void saveOrderNegativeDatabaseError() {
        OrdersEntity order = OrdersEntity.builder()
                .userId(1L)
                .totalAmount(14576.26)
                .build();

        when(jdbcTemplate.queryForObject(anyString(), eq(Long.class), any(Object[].class)))
                .thenThrow(new DataAccessException("Database error") {});

        assertThrows(DatabaseOperationException.class, () -> ordersRepository.saveOrder(order));
    }

    @Test
    void findByIdPositive() {
        OrdersEntity order = OrdersEntity.builder()
                .userId(1L)
                .orderId(1L)
                .totalAmount(144355.46)
                .build();

        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), any(Object[].class))).thenReturn(order);

        OrdersEntity foundOrder = ordersRepository.findById(1L);

        assertNotNull(foundOrder);
        assertEquals(1L, foundOrder.getOrderId());
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), any(RowMapper.class), any(Object[].class));
    }

    @Test
    void findByIdNegativeInvalidId() {
        assertThrows(InvalidDataException.class, () -> ordersRepository.findById(0L));
    }

    @Test
    void findByIdNegativeOrderNotFound() {
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), any(Object[].class)))
                .thenThrow(new EmptyResultDataAccessException(1));

        assertThrows(EntityNotFoundException.class, () -> ordersRepository.findById(1L));
    }

    @Test
    void findByIdNegativeDatabaseError() {
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), any(Object[].class)))
                .thenThrow(new DataAccessException("Database error") {});

        assertThrows(DatabaseOperationException.class, () -> ordersRepository.findById(1L));
    }

    @Test
    void findAllPositive() {
        OrdersEntity order = OrdersEntity.builder()
                .userId(1L)
                .orderId(1L)
                .totalAmount(36426.45)
                .build();

        OrdersEntity order2 = OrdersEntity.builder()
                .userId(2L)
                .orderId(2L)
                .totalAmount(144355.46)
                .build();

        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(List.of(order, order2));

        List<OrdersEntity> orders = ordersRepository.findAll();

        assertNotNull(orders);
        assertEquals(2, orders.size());
        verify(jdbcTemplate, times(1)).query(anyString(), any(RowMapper.class));
    }

    @Test
    void findAllNegativeDatabaseError() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenThrow(new DataAccessException("Database error") {});

        assertThrows(DatabaseOperationException.class, () -> ordersRepository.findAll());
    }

    @Test
    void updatePositive() {
        OrdersEntity order = OrdersEntity.builder()
                .userId(1L)
                .orderId(1L)
                .totalAmount(34235.89)
                .build();

        when(jdbcTemplate.update(anyString(), any(), any(), any())).thenReturn(1);

        ordersRepository.update(order);

        verify(jdbcTemplate, times(1)).update(anyString(), any(), any(), any());
    }

    @Test
    void updateNegativeInvalidData() {
        OrdersEntity order = OrdersEntity.builder()
                .orderId(null)
                .build();

        assertThrows(InvalidDataException.class, () -> ordersRepository.update(order));
    }

    @Test
    void updateNegativeDatabaseError() {
        OrdersEntity order = OrdersEntity.builder()
                .userId(1L)
                .orderId(1L)
                .totalAmount(52345235.87)
                .build();

        when(jdbcTemplate.update(anyString(), any(), any(), any()))
                .thenThrow(new DataAccessException("Database error") {});

        assertThrows(DatabaseOperationException.class, () -> ordersRepository.update(order));
    }

    @Test
    void deleteByIdNegativeInvalidId() {
        assertThrows(InvalidDataException.class, () -> ordersRepository.deleteById(0L));
    }

    @Test
    void deleteAllPositive() {
        when(jdbcTemplate.update(anyString())).thenReturn(1);

        ordersRepository.deleteAll();

        verify(jdbcTemplate, times(1)).update(anyString());
    }

    @Test
    void deleteAllNegativeDatabaseError() {
        when(jdbcTemplate.update(anyString()))
                .thenThrow(new DataAccessException("Database error") {});

        assertThrows(DatabaseOperationException.class, () -> ordersRepository.deleteAll());
    }
}