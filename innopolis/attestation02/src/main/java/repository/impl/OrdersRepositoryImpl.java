package repository.impl;

import config.JDBCTemplateLink;
import entity.OrdersEntity;
import entity.ProductsEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import repository.OrdersRepository;

import java.sql.Timestamp;
import java.util.List;

public class OrdersRepositoryImpl implements OrdersRepository {


    @Override
    public OrdersEntity saveOrder(OrdersEntity order) {
        String sql = "INSERT INTO online_electronics_store.orders (user_id, total_amount) VALUES (?, ?) RETURNING order_id";
        Long orderId = JDBCTemplateLink.jdbcTemplate().queryForObject(sql, new Object[]{order.getUserId(), order.getTotalAmount()}, Long.class);
        order.setOrderId(orderId);
        return order;
    }

    @Override
    public OrdersEntity findById(Long orderId) {
        String sql = "SELECT  FROM online_electronics_store.orders WHERE order_id = ?";
        return JDBCTemplateLink.jdbcTemplate().queryForObject(sql, new Object[]{orderId}, ordersMapper);
    }

    @Override
    public List<OrdersEntity> findAll() {
        return JDBCTemplateLink.jdbcTemplate().query("SELECT * FROM online_electronics_store.orders", ordersMapper);
    }

    @Override
    public void update(OrdersEntity order) {
        String sql = "UPDATE online_electronics_store.orders SET user_id = ?, total_amount = ? WHERE order_id = ?";
        JDBCTemplateLink.jdbcTemplate().update(sql, order.getUserId(), order.getTotalAmount(), order.getOrderId());
    }

    @Override
    public void deleteById(Long orderId) {
        JDBCTemplateLink.jdbcTemplate().update("DELETE FROM online_electronics_store.orders WHERE order_id = ?", orderId);
    }

    @Override
    public void deleteAll() {
        JDBCTemplateLink.jdbcTemplate().update("DELETE FROM online_electronics_store.orders");
    }

    private static final RowMapper<OrdersEntity> ordersMapper = (row, rowNumber) ->
            OrdersEntity.builder()
                .orderId(row.getLong("order_id"))
                .userId(row.getLong("user_id"))
                .totalAmount(row.getDouble("total_amount"))
                .build();

}
