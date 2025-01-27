package ru.innopolis.entity;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemsEntity {

   private Long orderItemId;

   private Long orderId;

   private Long productId;

   private Double quantity;

   private Double price;
}
