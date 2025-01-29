package ru.innopolis.repository.impl;

import org.springframework.jdbc.core.RowMapper;
import ru.innopolis.config.JDBCTemplateConfig;
import ru.innopolis.entity.OrderItemsEntity;
import ru.innopolis.repository.OrderItemsRepository;

import java.util.List;
import java.util.stream.Collectors;

public class OrderItemsRepositoryImpl implements OrderItemsRepository {


    @Override
    public OrderItemsEntity saveOrderItem(OrderItemsEntity orderItem) {
        String sql = "INSERT INTO online_electronics_store.order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?) RETURNING order_item_id";
        Long orderItemId = JDBCTemplateConfig.jdbcTemplate().queryForObject(sql, new Object[]{orderItem.getOrderId(), orderItem.getProductId(), orderItem.getQuantity(), orderItem.getPrice()}, Long.class);
        orderItem.setOrderItemId(orderItemId);
        return orderItem;
    }

    @Override
    public OrderItemsEntity findById(Long orderItemId) {
        String sql = "SELECT * FROM online_electronics_store.order_items WHERE order_item_id = ?";
        return JDBCTemplateConfig.jdbcTemplate().queryForObject(sql, new Object[]{orderItemId}, orderItemsMapper);
    }

    @Override
    public List<OrderItemsEntity> findAll() {
        String sql = "SELECT * FROM online_electronics_store.order_items";
        return JDBCTemplateConfig.jdbcTemplate().query(sql, orderItemsMapper);
    }
    
    @Override
    public List<OrderItemsEntity> findAllByQuantity(int quantity) {
        return findAll().stream()
                .filter(order -> order.getQuantity() == quantity)
                .collect(Collectors.toList());
    }

    @Override
    public void update(OrderItemsEntity orderItem) {
        String sql = "UPDATE online_electronics_store.order_items SET order_id = ?, product_id = ?, quantity = ?, price = ? WHERE order_item_id = ?";
        JDBCTemplateConfig.jdbcTemplate().update(sql, orderItem.getOrderId(), orderItem.getProductId(), orderItem.getQuantity(), orderItem.getPrice(), orderItem.getOrderItemId());
    }

    @Override
    public void deleteById(Long orderItemId) {
        JDBCTemplateConfig.jdbcTemplate().update("DELETE FROM online_electronics_store.order_items WHERE order_item_id = ?", orderItemId);
    }

    @Override
    public void deleteAll() {
        JDBCTemplateConfig.jdbcTemplate().update("DELETE FROM online_electronics_store.order_items");
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
