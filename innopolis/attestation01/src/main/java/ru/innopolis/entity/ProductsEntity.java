package ru.innopolis.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProductsEntity {

    private Long productId;

    private String productName;

    private String description;

    private Double price;

    private Integer stock;

}
