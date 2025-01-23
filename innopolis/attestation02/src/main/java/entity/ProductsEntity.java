package entity;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductsEntity {

    private Long productId;

    private String productName;

    private String description;

    private Double price;

    private Integer stock;

}
