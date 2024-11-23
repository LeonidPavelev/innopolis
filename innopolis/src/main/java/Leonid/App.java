package Leonid;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class App {
    public static void main(String[] args) {
        Product water = new Product();
        water.setId(UUID.randomUUID());
        water.setDescription("Water");
        water.setPrice(75.7);
        water.setCount(2);
        Product chocolate = new Product(UUID.randomUUID(), "Chocolate", 130.4, 7);
        List<Product> products = new ArrayList<>();
        products.add(water);
        products.add(chocolate);
        System.out.println(products);
    }
}
