package ru.innopolis.repository;

import ru.innopolis.entity.OrdersEntity;

import java.util.List;

public interface OrdersRepository {

    List<OrdersEntity> findAll();
}
