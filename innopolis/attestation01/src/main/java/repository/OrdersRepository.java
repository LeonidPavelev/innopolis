package repository;

import entity.OrdersEntity;

import java.util.List;

public interface OrdersRepository {

    List<OrdersEntity> findAll();
}
