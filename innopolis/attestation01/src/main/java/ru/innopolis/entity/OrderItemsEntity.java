package ru.innopolis.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderItemsEntity {

    private Long orderItemId;

    private Long orderId;

    private Long productId;

    private Integer quantity;

    private Double price;
}
