package model;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class OrderItems {

    int orderItemId;

    int orderId;

    int productId;

    int quantity;

    double price;
}
