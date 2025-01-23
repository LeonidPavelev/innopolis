package repository.impl;

import config.JDBCTemplateLink;
import entity.ProductsEntity;
import org.springframework.jdbc.core.RowMapper;
import repository.ProductsRepository;

import java.util.List;

public class ProductsRepositoryImpl implements ProductsRepository {

    @Override
    public ProductsEntity saveProduct(ProductsEntity product) {
        String sql = "INSERT INTO online_electronics_store.products (product_name, description, price, stock) VALUES (?, ?, ?, ?) RETURNING product_id";
        Long productId = JDBCTemplateLink.jdbcTemplate().queryForObject(sql, new Object[]{product.getProductName(), product.getDescription(), product.getPrice(), product.getStock()}, Long.class);
        product.setProductId(productId);
        return product;
    }

    @Override
    public ProductsEntity findById(int productId) {
        String sql = "SELECT * FROM online_electronics_store.products WHERE product_id = ?";
        return JDBCTemplateLink.jdbcTemplate().queryForObject(sql, new Object[]{productId}, productsMapper);
    }

    @Override
    public List<ProductsEntity> findAll() {
        return JDBCTemplateLink.jdbcTemplate().query("SELECT * FROM online_electronics_store.products", productsMapper);
    }

    @Override
    public void updateProduct(ProductsEntity product) {
        String sql = "UPDATE online_electronics_store.products SET product_name = ?, description = ?, price = ?, stock = ? WHERE product_id = ?";
        JDBCTemplateLink.jdbcTemplate().update(sql, product.getProductName(), product.getDescription(), product.getPrice(), product.getStock(), product.getProductId());
    }

    @Override
    public void deleteProductById(int productId) {
        JDBCTemplateLink.jdbcTemplate().update("DELETE FROM online_electronics_store.products WHERE product_id = ?", productId);
    }

    @Override
    public void deleteAll() {
        JDBCTemplateLink.jdbcTemplate().update("DELETE FROM online_electronics_store.products");
    }

    private static final RowMapper<ProductsEntity> productsMapper = (row, rowNumber) ->
            ProductsEntity.builder()
                    .productId(row.getLong("product_id"))
                    .productName(row.getString("product_name"))
                    .description(row.getString("description"))
                    .price(row.getDouble("price"))
                    .stock(row.getInt("stock"))
                    .build();
}
