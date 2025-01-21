package model;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.sql.Timestamp;

@Value
@Builder
@Jacksonized
public class Orders {

    int orderId;

    int userId;

    Timestamp orderDate;

    double totalAmount;
}
