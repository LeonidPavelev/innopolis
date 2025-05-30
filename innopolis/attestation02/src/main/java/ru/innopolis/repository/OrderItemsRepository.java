package ru.innopolis.repository;

import ru.innopolis.entity.OrderItemsEntity;

import java.util.List;

public interface OrderItemsRepository {

    OrderItemsEntity saveOrderItem (OrderItemsEntity orderItem);

    OrderItemsEntity findById (Long orderItemId);

    List<OrderItemsEntity> findAll();

    List<OrderItemsEntity> findAllByQuantity(int quantity);

    void update (OrderItemsEntity orderItem);

    void deleteById (Long orderItemId);

    void deleteAll();
}
