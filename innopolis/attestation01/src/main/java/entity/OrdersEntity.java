package entity;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.sql.Timestamp;

@Value
@Builder
@Jacksonized
public class OrdersEntity {

    int orderId;

    int userId;

    Timestamp orderDate;

    double totalAmount;
}
