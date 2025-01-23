package repository.impl;

import config.JDBCTemplateLink;
import entity.OrderItemsEntity;
import org.springframework.jdbc.core.RowMapper;
import repository.OrderItemsRepository;

import java.util.List;

public class OrderItemsRepositoryImpl implements OrderItemsRepository {


    @Override
    public OrderItemsEntity saveOrderItem(OrderItemsEntity orderItem) {
        String sql = "INSERT INTO online_electronics_store.order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?) RETURNING order_item_id";
        Long orderItemId = JDBCTemplateLink.jdbcTemplate().queryForObject(sql, new Object[]{orderItem.getOrderId(), orderItem.getProductId(), orderItem.getQuantity(), orderItem.getPrice()}, Long.class);
        orderItem.setOrderItemId(orderItemId);
        return orderItem;
    }

    @Override
    public OrderItemsEntity findById(Long orderItemId) {
        String sql = "SELECT * FROM online_electronics_store.order_items WHERE order_item_id = ?";
        return JDBCTemplateLink.jdbcTemplate().queryForObject(sql, new Object[]{orderItemId}, orderItemsMapper);
    }

    @Override
    public List<OrderItemsEntity> findAll() {
        String sql = "SELECT * FROM online_electronics_store.order_items";
        return JDBCTemplateLink.jdbcTemplate().query(sql, orderItemsMapper);
    }

    @Override
    public void update(OrderItemsEntity orderItem) {
        String sql = "UPDATE online_electronics_store.order_items SET order_id = ?, product_id = ?, quantity = ?, price = ? WHERE order_item_id = ?";
        JDBCTemplateLink.jdbcTemplate().update(sql, orderItem.getOrderId(), orderItem.getProductId(), orderItem.getQuantity(), orderItem.getPrice(), orderItem.getOrderItemId());
    }

    @Override
    public void deleteById(Long orderItemId) {
        JDBCTemplateLink.jdbcTemplate().update("DELETE FROM online_electronics_store.order_items WHERE order_item_id = ?", orderItemId);
    }

    @Override
    public void deleteAll() {
        JDBCTemplateLink.jdbcTemplate().update("DELETE FROM online_electronics_store.order_items");
    }
    private static final RowMapper<OrderItemsEntity> orderItemsMapper = (row, rowNumber) ->
            OrderItemsEntity.builder()
                    .orderItemId(row.getLong("order_item_id"))
                    .orderId(row.getLong("order_id"))
                    .productId(row.getLong("product_id"))
                    .quantity(row.getDouble("quantity"))
                    .price(row.getDouble("price"))
                    .build();
}
