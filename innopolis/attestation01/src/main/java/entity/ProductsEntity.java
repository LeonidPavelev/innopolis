package entity;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class ProductsEntity {

    int productId;

    String productName;

    String description;

    Double price;

    Integer stock;

}
