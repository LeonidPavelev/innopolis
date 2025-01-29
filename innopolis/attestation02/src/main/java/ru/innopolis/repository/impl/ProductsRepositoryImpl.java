package ru.innopolis.repository.impl;

import ru.innopolis.config.JDBCTemplateConfig;
import ru.innopolis.entity.ProductsEntity;
import org.springframework.jdbc.core.RowMapper;
import ru.innopolis.repository.ProductsRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductsRepositoryImpl implements ProductsRepository {

    @Override
    public ProductsEntity saveProduct(ProductsEntity product) {
        String sql = "INSERT INTO online_electronics_store.products (product_name, description, price, stock) VALUES (?, ?, ?, ?) RETURNING product_id";
        Long productId = JDBCTemplateConfig.jdbcTemplate().queryForObject(sql, new Object[]{product.getProductName(), product.getDescription(), product.getPrice(), product.getStock()}, Long.class);
        product.setProductId(productId);
        return product;
    }

    @Override
    public ProductsEntity findById(int productId) {
        String sql = "SELECT * FROM online_electronics_store.products WHERE product_id = ?";
        return JDBCTemplateConfig.jdbcTemplate().queryForObject(sql, new Object[]{productId}, productsMapper);
    }

    @Override
    public List<ProductsEntity> findAll() {
        return JDBCTemplateConfig.jdbcTemplate().query("SELECT * FROM online_electronics_store.products", productsMapper);
    }

    @Override
    public List<ProductsEntity> findAllSortedByPrice() {
        return findAll().stream()
                .sorted(Comparator.comparingDouble(ProductsEntity::getPrice))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductsEntity> findAllByStockGreaterThan(int stock) {
        return findAll().stream()
                .filter(product -> product.getStock() > stock)
                .collect(Collectors.toList());
    }

    @Override
    public void updateProduct(ProductsEntity product) {
        String sql = "UPDATE online_electronics_store.products SET product_name = ?, description = ?, price = ?, stock = ? WHERE product_id = ?";
        JDBCTemplateConfig.jdbcTemplate().update(sql, product.getProductName(), product.getDescription(), product.getPrice(), product.getStock(), product.getProductId());
    }

    @Override
    public void deleteProductById(int productId) {
        JDBCTemplateConfig.jdbcTemplate().update("DELETE FROM online_electronics_store.products WHERE product_id = ?", productId);
    }

    @Override
    public void deleteAll() {
        JDBCTemplateConfig.jdbcTemplate().update("DELETE FROM online_electronics_store.products");
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
