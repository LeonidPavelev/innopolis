package ru.innopolis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.innopolis.data.entity.Order;
import ru.innopolis.data.repository.OrderRepository;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order createOrder(Order order) {
        if (order.getQuantity() <= 0 || order.getAmount() <= 0) {
            throw new IllegalArgumentException("Количество и сумма заказа должны быть больше нуля");
        }
        return orderRepository.save(order);
    }

    public long getOrderCount() {
        return orderRepository.count();
    }

    public double getAverageOrderAmount() {
        return orderRepository.findAll().stream()
                .mapToDouble(Order::getAmount)
                .average()
                .orElse(0.0);
    }
}
