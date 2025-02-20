package ru.innopolis.pojo;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class OrderPojo {

    String productId;
    int quantity;
    double amount;
}
