package leonid.controllers;

import leonid.models.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class Controller {

    @GetMapping("/ping")
    public String ping() {
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
        return products.toString();
    }
}
