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
    public List<OrdersEntity> findAll() {
        return JDBCTemplateLink.jdbcTemplate().query("SELECT * FROM online_electronics_store.orders", ordersMapper);
    }

    private static final RowMapper<OrdersEntity> ordersMapper = (row, rowNumber) ->
            OrdersEntity.builder()
                .orderId(row.getInt("order_id"))
                .userId(row.getInt("user_id"))
                .orderDate(row.getTimestamp("order_date"))
                .totalAmount(row.getDouble("total_amount"))
                .build();

}
