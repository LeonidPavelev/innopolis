package ru.innopolis.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.innopolis.entity.ProductsEntity;
import ru.innopolis.exceptions.DatabaseOperationException;
import ru.innopolis.exceptions.EntityNotFoundException;
import ru.innopolis.exceptions.InvalidDataException;
import ru.innopolis.repository.ProductsRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
@RequiredArgsConstructor
public class ProductsRepositoryImpl implements ProductsRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public ProductsEntity saveProduct(ProductsEntity product) {
        if (product == null || product.getProductName() == null || product.getPrice() <= 0 || product.getStock() < 0) {
            throw new InvalidDataException("Invalid product data");
        }
        String sql = "INSERT INTO online_electronics_store.products (product_name, description, price, stock) VALUES (?, ?, ?, ?) RETURNING product_id";
        try {
            Long productId = jdbcTemplate.queryForObject(sql, Long.class, product.getProductName(), product.getDescription(), product.getPrice(), product.getStock());
            product.setProductId(productId);
            return product;
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Error saving product", e);
        }
    }

    @Override
    public ProductsEntity findById(int productId) {
        if (productId <= 0) {
            throw new InvalidDataException("Invalid product ID");
        }
        try {
            String sql = "SELECT * FROM online_electronics_store.products WHERE product_id = ?";
            return jdbcTemplate.queryForObject(sql, productsMapper, productId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Product with id " + productId + " not found");
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Error accessing the database", e);
        }
    }

    @Override
    public List<ProductsEntity> findAll() {
        try {
            return jdbcTemplate.query("SELECT * FROM online_electronics_store.products", productsMapper);
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Error accessing the database", e);
        }
    }

    @Override
    public List<ProductsEntity> findAllSortedByPrice() {
        try {
            return findAll().stream()
                    .sorted(Comparator.comparingDouble(ProductsEntity::getPrice))
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Error accessing the database", e);
        }
    }

    @Override
    public List<ProductsEntity> findAllByStockGreaterThan(int stock) {
        if (stock < 0) {
            throw new InvalidDataException("Invalid stock value");
        }
        try {
            return findAll().stream()
                    .filter(product -> product.getStock() > stock)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Error accessing the database", e);
        }
    }

    @Override
    public void updateProduct(ProductsEntity product) {
        if (
                product == null ||
                product.getProductId() == null ||
                product.getProductName() == null ||
                product.getPrice() <= 0 ||
                product.getStock() < 0
        ) {
            throw new InvalidDataException("Invalid product data");
        }
        try {
            String sql = "UPDATE online_electronics_store.products SET product_name = ?, description = ?, price = ?, stock = ? WHERE product_id = ?";
            jdbcTemplate.update(sql, product.getProductName(), product.getDescription(), product.getPrice(), product.getStock(), product.getProductId());
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Error updating product", e);
        }
    }

    @Override
    public void deleteProductById(int productId) {
        if (productId <= 0) {
            throw new InvalidDataException("Invalid product ID");
        }
        try {
            jdbcTemplate.update("DELETE FROM online_electronics_store.products WHERE product_id = ?", productId);
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Error deleting product", e);
        }
    }

    @Override
    public void deleteAll() {
        try {
            jdbcTemplate.update("DELETE FROM online_electronics_store.products");
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Error deleting all products", e);
        }
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