package innopolis;

import config.JDBCTemplateLink;
import model.Products;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class Main {

    private static final JdbcTemplate jdbcTemplate = JDBCTemplateLink.jdbcTemplate();

    public static void main(String[] args) {

        List<Products> products = jdbcTemplate.query("SELECT * FROM online_electronics_store.products", productsMapper);

        System.out.println(products);

    }

    private static final RowMapper<Products> productsMapper = (row, rowNumber) -> {
        Long id = row.getLong("id");
        String name = row.getString("name");
        Long price = row.getLong("price");
        Long quantity = row.getLong("quantity");
        return Products.builder()
                .id(id)
                .name(name)
                .price(price)
                .quantity(quantity)
                .build();
    };
}