package ru.innopolis.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.innopolis.data.entity.Order;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
