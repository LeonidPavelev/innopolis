package entity;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class OrderItemsEntity {

    int orderItemId;

    int orderId;

    int productId;

    int quantity;

    double price;
}
