package ru.innopolis.repository;

import ru.innopolis.entity.OrderItemsEntity;

import java.util.List;

public interface OrderItemsRepository {

    List<OrderItemsEntity> findAll();
}
