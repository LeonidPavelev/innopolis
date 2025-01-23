package repository;

import entity.OrdersEntity;

import java.util.List;

public interface OrdersRepository {

    OrdersEntity saveOrder (OrdersEntity order);

    OrdersEntity findById (Long orderId);

    List<OrdersEntity> findAll();

    void update (OrdersEntity order);

    void deleteById (Long orderId);

    void deleteAll();
}
