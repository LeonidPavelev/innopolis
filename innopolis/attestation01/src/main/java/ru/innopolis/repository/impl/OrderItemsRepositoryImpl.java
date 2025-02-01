package ru.innopolis.repository.impl;

import org.springframework.jdbc.core.RowMapper;
import ru.innopolis.config.JDBCTemplateConfig;
import ru.innopolis.entity.OrderItemsEntity;
import ru.innopolis.repository.OrderItemsRepository;

import java.util.List;

public class OrderItemsRepositoryImpl implements OrderItemsRepository {
    @Override
    public List<OrderItemsEntity> findAll() {
        return JDBCTemplateConfig.jdbcTemplate().query("SELECT * FROM online_electronics_store.order_items", orderItemsMapper);
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
