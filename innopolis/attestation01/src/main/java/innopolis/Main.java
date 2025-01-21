package innopolis;

import config.JDBCTemplateLink;
import model.Products;
import model.Users;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Timestamp;
import java.util.List;

public class Main {

    private static final JdbcTemplate jdbcTemplate = JDBCTemplateLink.jdbcTemplate();

    public static void main(String[] args) {

        List<Products> products = jdbcTemplate.query("SELECT * FROM online_electronics_store.products", productsMapper);
        List<Users> users = jdbcTemplate.query("SELECT * FROM online_electronics_store.users", usersMapper);

        System.out.println(products);
        System.out.println(users);

    }

    private static final RowMapper<Products> productsMapper = (row, rowNumber) -> {
        String productName = row.getString("product_name");
        String description = row.getString("description");
        double price = row.getDouble("price");
        var stock = row.getInt("stock");
        return Products.builder()
                .productName(productName)
                .description(description)
                .price(price)
                .stock(stock)
                .build();
    };

    private static final RowMapper<Users> usersMapper = (row, rowNumber) -> {
        String firstName = row.getString("first_name");
        String lastName = row.getString("last_name");
        Timestamp createdAt = row.getTimestamp("created_at");
        return Users.builder()
                .firstName(firstName)
                .lastName(lastName)
                .createdAt(createdAt)
                .build();
    };
}