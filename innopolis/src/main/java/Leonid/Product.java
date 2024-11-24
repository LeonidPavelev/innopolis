package Leonid;

import lombok.*;

import java.util.UUID;
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private UUID id;
    private String description;
    private double price;
    private int count;

}

