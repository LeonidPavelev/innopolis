package entity;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdersEntity {

   private Long orderId;

   private Long userId;

   private Double totalAmount;
}
