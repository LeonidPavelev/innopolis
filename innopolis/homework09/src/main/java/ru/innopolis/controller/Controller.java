package ru.innopolis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.innopolis.data.entity.Order;
import ru.innopolis.pojo.OrderPojo;
import ru.innopolis.service.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class Controller {

    private final OrderService orderService;

    @PostMapping("/save")
    public ResponseEntity<Order> createOrder(@RequestBody OrderPojo order) {
        return ResponseEntity.ok(orderService.createOrder(convert(order)));
    }

    private Order convert(OrderPojo order) {
        return Order.builder()
                .productId(order.getProductId())
                .quantity(order.getQuantity())
                .amount(order.getAmount())
                .build();
    }
}
