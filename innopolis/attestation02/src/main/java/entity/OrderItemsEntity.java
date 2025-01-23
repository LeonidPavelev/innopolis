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
public class OrderItemsEntity {

   private Long orderItemId;

   private Long orderId;

   private Long productId;

   private Double quantity;

   private Double price;
}
