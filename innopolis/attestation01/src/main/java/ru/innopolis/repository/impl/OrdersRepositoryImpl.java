package ru.innopolis.repository.impl;

import ru.innopolis.config.JDBCTemplateConfig;
import ru.innopolis.entity.OrdersEntity;
import org.springframework.jdbc.core.RowMapper;
import ru.innopolis.repository.OrdersRepository;

import java.util.List;

public class OrdersRepositoryImpl implements OrdersRepository {


    @Override
    public List<OrdersEntity> findAll() {
        return JDBCTemplateConfig.jdbcTemplate().query("SELECT * FROM online_electronics_store.orders", ordersMapper);
    }

    private static final RowMapper<OrdersEntity> ordersMapper = (row, rowNumber) ->
            OrdersEntity.builder()
                .orderId(row.getLong("order_id"))
                .userId(row.getLong("user_id"))
                .orderDate(row.getTimestamp("order_date"))
                .totalAmount(row.getDouble("total_amount"))
                .build();

}
