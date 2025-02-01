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
import ru.innopolis.entity.OrderItemsEntity;
import ru.innopolis.exceptions.DatabaseOperationException;
import ru.innopolis.exceptions.EntityNotFoundException;
import ru.innopolis.exceptions.InvalidDataException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderItemsRepositoryImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private OrderItemsRepositoryImpl orderItemsRepository;

    @Test
    void saveOrderItemPositive() {
        OrderItemsEntity orderItem = OrderItemsEntity.builder()
                .orderId(1L)
                .orderItemId(1L)
                .productId(1L)
                .quantity(2)
                .price(3245.65)
                .build();

        when(jdbcTemplate.queryForObject(anyString(), eq(Long.class), any(Object[].class))).thenReturn(1L);

        OrderItemsEntity savedOrderItem = orderItemsRepository.saveOrderItem(orderItem);

        assertNotNull(savedOrderItem);
        assertEquals(1L, savedOrderItem.getOrderItemId());
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), eq(Long.class), any(Object[].class));
    }

    @Test
    void saveOrderItemNegativeInvalidData() {
        OrderItemsEntity orderItem = OrderItemsEntity.builder()
                .orderItemId(1L)
                .productId(null)
                .quantity(2)
                .price(3245.65)
                .build();

        assertThrows(InvalidDataException.class, () -> orderItemsRepository.saveOrderItem(orderItem));
    }

    @Test
    void findByIdPositive() {
        OrderItemsEntity orderItem = OrderItemsEntity.builder()
                .orderItemId(1L)
                .productId(1L)
                .quantity(2)
                .price(3265745.45)
                .build();

        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), any(Object[].class))).thenReturn(orderItem);

        OrderItemsEntity foundOrderItem = orderItemsRepository.findById(1L);

        assertNotNull(foundOrderItem);
        assertEquals(1L, foundOrderItem.getOrderItemId());
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), any(RowMapper.class), any(Object[].class));
    }

    @Test
    void findByIdNegativeInvalidId() {
        assertThrows(InvalidDataException.class, () -> orderItemsRepository.findById(0L));
    }

    @Test
    void findByIdNegativeOrderItemNotFound() {
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), any(Object[].class)))
                .thenThrow(new EmptyResultDataAccessException(1));

        assertThrows(EntityNotFoundException.class, () -> orderItemsRepository.findById(1L));
    }

    @Test
    void findByIdNegativeDatabaseError() {
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), any(Object[].class)))
                .thenThrow(new DataAccessException("Database error") {});

        assertThrows(DatabaseOperationException.class, () -> orderItemsRepository.findById(1L));
    }

    @Test
    void findAllPositive() {
        OrderItemsEntity orderItem = OrderItemsEntity.builder()
                .orderItemId(1L)
                .productId(1L)
                .quantity(2)
                .price(3212345.65)
                .build();

        OrderItemsEntity orderItem2 = OrderItemsEntity.builder()
                .orderItemId(2L)
                .productId(2L)
                .quantity(6)
                .price(325466645.95)
                .build();

        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(List.of(orderItem, orderItem2));

        List<OrderItemsEntity> orderItems = orderItemsRepository.findAll();

        assertNotNull(orderItems);
        assertEquals(2, orderItems.size());
        verify(jdbcTemplate, times(1)).query(anyString(), any(RowMapper.class));
    }

    @Test
    void findAllNegativeDatabaseError() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenThrow(new DataAccessException("Database error") {});

        assertThrows(DatabaseOperationException.class, () -> orderItemsRepository.findAll());
    }

    @Test
    void findAllByQuantityPositive() {
        OrderItemsEntity orderItem = OrderItemsEntity.builder()
                .orderItemId(1L)
                .productId(1L)
                .quantity(2)
                .price(3245435.75)
                .build();

        OrderItemsEntity orderItem2 = OrderItemsEntity.builder()
                .orderItemId(1L)
                .productId(1L)
                .quantity(2)
                .price(32876845.05)
                .build();

        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(List.of(orderItem, orderItem2));

        List<OrderItemsEntity> orderItems = orderItemsRepository.findAllByQuantity(2);

        assertNotNull(orderItems);
        assertEquals(2, orderItems.size());
        verify(jdbcTemplate, times(1)).query(anyString(), any(RowMapper.class));
    }

    @Test
    void findAllByQuantityNegativeInvalidQuantity() {
        assertThrows(InvalidDataException.class, () -> orderItemsRepository.findAllByQuantity(0));
    }

    @Test
    void findAllByQuantityNegativeDatabaseError() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenThrow(new DataAccessException("Database error") {});

        assertThrows(DatabaseOperationException.class, () -> orderItemsRepository.findAllByQuantity(2));
    }

    @Test
    void updatePositive() {
        OrderItemsEntity orderItem = OrderItemsEntity.builder()
                .orderId(1L)
                .orderItemId(1L)
                .productId(1L)
                .quantity(2)
                .price(3223145.55)
                .build();

        when(jdbcTemplate.update(anyString(), any(), any(), any(), any(), any())).thenReturn(1);

        orderItemsRepository.update(orderItem);

        verify(jdbcTemplate, times(1)).update(anyString(), any(), any(), any(), any(), any());
    }

    @Test
    void deleteByIdNegativeInvalidId() {
        assertThrows(InvalidDataException.class, () -> orderItemsRepository.deleteById(0L));
    }

    @Test
    void deleteAllPositive() {
        when(jdbcTemplate.update(anyString())).thenReturn(1);

        orderItemsRepository.deleteAll();

        verify(jdbcTemplate, times(1)).update(anyString());
    }

    @Test
    void deleteAllNegativeDatabaseError() {
        when(jdbcTemplate.update(anyString()))
                .thenThrow(new DataAccessException("Database error") {});

        assertThrows(DatabaseOperationException.class, () -> orderItemsRepository.deleteAll());
    }
}