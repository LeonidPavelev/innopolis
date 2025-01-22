package repository.impl;

import config.JDBCTemplateLink;
import entity.ProductsEntity;
import org.springframework.jdbc.core.RowMapper;
import repository.ProductsRepository;

import java.util.List;

public class ProductsRepositoryImpl implements ProductsRepository {

    @Override
    public List<ProductsEntity> findAll() {
        return JDBCTemplateLink.jdbcTemplate().query("SELECT * FROM online_electronics_store.products", productsMapper);
    }

    private static final RowMapper<ProductsEntity> productsMapper = (row, rowNumber) ->
            ProductsEntity.builder()
                    .productId(row.getInt("product_id"))
                    .productName(row.getString("product_name"))
                    .description(row.getString("description"))
                    .price(row.getDouble("price"))
                    .stock(row.getInt("stock"))
                    .build();

}
