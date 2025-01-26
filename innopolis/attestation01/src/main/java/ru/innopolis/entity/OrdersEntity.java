package ru.innopolis.entity;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrdersEntity {

    private Long orderId;

    private Long userId;

    private Timestamp orderDate;

    private Double totalAmount;
}
