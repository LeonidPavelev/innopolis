package ru.innopolis.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.innopolis.entity.OrderItemsEntity;
import ru.innopolis.exceptions.DatabaseOperationException;
import ru.innopolis.exceptions.EntityNotFoundException;
import ru.innopolis.exceptions.InvalidDataException;
import ru.innopolis.repository.OrderItemsRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class OrderItemsRepositoryImpl implements OrderItemsRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public OrderItemsEntity saveOrderItem(OrderItemsEntity orderItem) {
        if (
                orderItem == null
                        || orderItem.getOrderId() == null
                        || orderItem.getProductId() == null
                        || orderItem.getQuantity() <= 0
                        || orderItem.getPrice() <= 0
        ) {
            throw new InvalidDataException("Invalid order item data");
        }
        String sql = "INSERT INTO online_electronics_store.order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?) RETURNING order_item_id";
        try {
            Long orderItemId = jdbcTemplate.queryForObject(sql, Long.class, orderItem.getOrderId(), orderItem.getProductId(), orderItem.getQuantity(), orderItem.getPrice());
            orderItem.setOrderItemId(orderItemId);
            return orderItem;
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Error saving order item", e);
        }
    }

    @Override
    public OrderItemsEntity findById(Long orderItemId) {
        if (orderItemId == null || orderItemId <= 0) {
            throw new InvalidDataException("Invalid order item ID");
        }
        try {
            String sql = "SELECT * FROM online_electronics_store.order_items WHERE order_item_id = ?";
            return jdbcTemplate.queryForObject(sql, orderItemsMapper, orderItemId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Order item with id " + orderItemId + " not found");
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Error accessing the database", e);
        }
    }

    @Override
    public List<OrderItemsEntity> findAll() {
        try {
            String sql = "SELECT * FROM online_electronics_store.order_items";
            return jdbcTemplate.query(sql, orderItemsMapper);
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Error accessing the database", e);
        }
    }

    @Override
    public List<OrderItemsEntity> findAllByQuantity(int quantity) {
        if (quantity <= 0) {
            throw new InvalidDataException("Invalid quantity");
        }
        try {
            return findAll().stream()
                    .filter(order -> order.getQuantity() == quantity)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Error accessing the database", e);
        }
    }

    @Override
    public void update(OrderItemsEntity orderItem) {
        if (
                orderItem == null
                        || orderItem.getOrderItemId() == null
                        || orderItem.getOrderId() == null
                        || orderItem.getProductId() == null
                        || orderItem.getQuantity() <= 0
                        || orderItem.getPrice() <= 0
        ) {
            throw new InvalidDataException("Invalid order item data");
        }
        try {
            String sql = "UPDATE online_electronics_store.order_items SET order_id = ?, product_id = ?, quantity = ?, price = ? WHERE order_item_id = ?";
            jdbcTemplate.update(sql, orderItem.getOrderId(), orderItem.getProductId(), orderItem.getQuantity(), orderItem.getPrice(), orderItem.getOrderItemId());
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Error updating order item", e);
        }
    }
    @Override
    public void deleteById(Long orderItemId) {
        if (orderItemId == null || orderItemId <= 0) {
            throw new InvalidDataException("Invalid order item ID");
        }
        try {
            jdbcTemplate.update("DELETE FROM online_electronics_store.order_items WHERE order_item_id = ?", orderItemId);
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Error deleting order item", e);
        }
    }

    @Override
    public void deleteAll() {
        try {
            jdbcTemplate.update("DELETE FROM online_electronics_store.order_items");
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Error deleting all order items", e);
        }
    }

    private static final RowMapper<OrderItemsEntity> orderItemsMapper = (row, rowNumber) ->
            OrderItemsEntity.builder()
                    .orderItemId(row.getLong("order_item_id"))
                    .orderId(row.getLong("order_id"))
                    .productId(row.getLong("product_id"))
                    .quantity(row.getInt("quantity"))
                    .price(row.getDouble("price"))
                    .build();
}
