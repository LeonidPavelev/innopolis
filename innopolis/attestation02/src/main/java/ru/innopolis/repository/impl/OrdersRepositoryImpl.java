package ru.innopolis.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.innopolis.entity.OrdersEntity;
import ru.innopolis.exceptions.DatabaseOperationException;
import ru.innopolis.exceptions.EntityNotFoundException;
import ru.innopolis.exceptions.InvalidDataException;
import ru.innopolis.repository.OrdersRepository;

import java.util.List;

@RequiredArgsConstructor
public class OrdersRepositoryImpl implements OrdersRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public OrdersEntity saveOrder(OrdersEntity order) {
        if (order == null || order.getUserId() == null || order.getTotalAmount() <= 0) {
            throw new InvalidDataException("Invalid order data");
        }
        String sql = "INSERT INTO online_electronics_store.orders (user_id, total_amount) VALUES (?, ?) RETURNING order_id";
        try {
            Long orderId = jdbcTemplate.queryForObject(sql, Long.class, order.getUserId(), order.getTotalAmount());
            order.setOrderId(orderId);
            return order;
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Error saving order", e);
        }
    }

    @Override
    public OrdersEntity findById(Long orderId) {
        if (orderId == null || orderId <= 0) {
            throw new InvalidDataException("Invalid order ID");
        }
        try {
            String sql = "SELECT * FROM online_electronics_store.orders WHERE order_id = ?";
            return jdbcTemplate.queryForObject(sql,ordersMapper, orderId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Order with id " + orderId + " not found");
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Error accessing the database", e);
        }
    }

    @Override
    public List<OrdersEntity> findAll() {
        try {
            return jdbcTemplate.query("SELECT * FROM online_electronics_store.orders", ordersMapper);
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Error accessing the database", e);
        }
    }

    @Override
    public void update(OrdersEntity order) {
        if (order == null || order.getOrderId() == null || order.getUserId() == null || order.getTotalAmount() <= 0) {
            throw new InvalidDataException("Invalid order data");
        }
        try {
            String sql = "UPDATE online_electronics_store.orders SET user_id = ?, total_amount = ? WHERE order_id = ?";
            jdbcTemplate.update(sql, order.getUserId(), order.getTotalAmount(), order.getOrderId());
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Error updating order", e);
        }
    }

    @Override
    public void deleteById(Long orderId) {
        if (orderId == null || orderId <= 0) {
            throw new InvalidDataException("Invalid order ID");
        }
        try {
            jdbcTemplate.update("DELETE FROM online_electronics_store.orders WHERE order_id = ?", orderId);
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Error deleting order", e);
        }
    }

    @Override
    public void deleteAll() {
        try {
            jdbcTemplate.update("DELETE FROM online_electronics_store.orders");
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Error deleting all orders", e);
        }
    }

    private static final RowMapper<OrdersEntity> ordersMapper = (row, rowNumber) ->
            OrdersEntity.builder()
                    .orderId(row.getLong("order_id"))
                    .userId(row.getLong("user_id"))
                    .totalAmount(row.getDouble("total_amount"))
                    .build();
}