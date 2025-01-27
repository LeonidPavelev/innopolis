package ru.innopolis.entity;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdersEntity {

   private Long orderId;

   private Long userId;

   private Double totalAmount;
}
