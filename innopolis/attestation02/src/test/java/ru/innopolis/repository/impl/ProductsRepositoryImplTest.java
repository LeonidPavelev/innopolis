package ru.innopolis.repository.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.innopolis.entity.ProductsEntity;
import ru.innopolis.exceptions.DatabaseOperationException;
import ru.innopolis.exceptions.EntityNotFoundException;
import ru.innopolis.exceptions.InvalidDataException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class ProductsRepositoryImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private ProductsRepositoryImpl productsRepository;

    @Test
    void saveProductPositive() {
        ProductsEntity product = ProductsEntity.builder()
                .productName("Laptop")
                .description("High-end gaming laptop")
                .price(150.05)
                .stock(4)
                .build();

        when(jdbcTemplate.queryForObject(anyString(), eq(Long.class), any(Object[].class))).thenReturn(1L);

        ProductsEntity savedProduct = productsRepository.saveProduct(product);

        assertNotNull(savedProduct);
        assertEquals(1L, savedProduct.getProductId());
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), eq(Long.class), any(Object[].class));
    }

    @Test
    void saveProductNegativeInvalidData() {
        ProductsEntity product = ProductsEntity.builder()
                .productName(null)
                .price(170.63)
                .stock(98)
                .build();

        assertThrows(InvalidDataException.class, () -> productsRepository.saveProduct(product));
    }

    @Test
    void saveProductNegativeDatabaseError() {
        ProductsEntity product = ProductsEntity.builder()
                .productName("Laptop")
                .description("High-end gaming laptop")
                .price(180.05)
                .stock(7)
                .build();

        when(jdbcTemplate.queryForObject(anyString(),eq(Long.class), any(Object[].class)))
                .thenThrow(new DataAccessException("Database error") {});

        assertThrows(DatabaseOperationException.class, () -> productsRepository.saveProduct(product));
    }

    @Test
    void findByIdPositive() {
        ProductsEntity product = ProductsEntity.builder()
                .productId(1L)
                .productName("Laptop")
                .description("High-end gaming laptop")
                .price(190.35)
                .stock(4)
                .build();

        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), any(Object[].class))).thenReturn(product);

        ProductsEntity foundProduct = productsRepository.findById(1);

        assertNotNull(foundProduct);
        assertEquals(1L, foundProduct.getProductId());
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), any(RowMapper.class), any(Object[].class));
    }

    @Test
    void findByIdNegativeInvalidId() {
        assertThrows(InvalidDataException.class, () -> productsRepository.findById(0));
    }

    @Test
    void findByIdNegativeProductNotFound() {
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), any(Object[].class)))
                .thenThrow(new EmptyResultDataAccessException(1));

        assertThrows(EntityNotFoundException.class, () -> productsRepository.findById(1));
    }

    @Test
    void findByIdNegativeDatabaseError() {
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), any(Object[].class)))
                .thenThrow(new DataAccessException("Database error") {});

        assertThrows(DatabaseOperationException.class, () -> productsRepository.findById(1));
    }
    @Test
    void findAllPositive() {
        ProductsEntity product = ProductsEntity.builder()
                .productName("Laptop")
                .price(550.05)
                .stock(7)
                .build();

        ProductsEntity product2 = ProductsEntity.builder()
                .productName("Smartphone")
                .price(780.37)
                .stock(6)
                .build();

        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(List.of(product, product2));

        List<ProductsEntity> products = productsRepository.findAll();

        assertNotNull(products);
        assertEquals(2, products.size());
        verify(jdbcTemplate, times(1)).query(anyString(), any(RowMapper.class));
    }

    @Test
    void findAllNegativeDatabaseError() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenThrow(new DataAccessException("Database error") {});

        assertThrows(DatabaseOperationException.class, () -> productsRepository.findAll());
    }

    @Test
    void findAllSortedByPricePositive() {
        ProductsEntity product = ProductsEntity.builder()
                .productName("Laptop")
                .price(550.05)
                .stock(7)
                .build();

        ProductsEntity product2 = ProductsEntity.builder()
                .productName("Smartphone")
                .price(780.37)
                .stock(6)
                .build();

        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(List.of(product, product2));

        List<ProductsEntity> products = productsRepository.findAllSortedByPrice();

        assertNotNull(products);
        assertEquals(2, products.size());
        assertEquals(550.05, products.get(0).getPrice());
        assertEquals(780.37, products.get(1).getPrice());
        verify(jdbcTemplate, times(1)).query(anyString(), any(RowMapper.class));
    }

    @Test
    void findAllSortedByPriceNegativeDatabaseError() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenThrow(new DataAccessException("Database error") {});

        assertThrows(DatabaseOperationException.class, () -> productsRepository.findAllSortedByPrice());
    }

    @Test
    void findAllByStockGreaterThanPositive() {
        ProductsEntity product = ProductsEntity.builder()
                .productName("Laptop")
                .price(4550.05)
                .stock(7)
                .build();

        ProductsEntity product2 = ProductsEntity.builder()
                .productName("Smartphone")
                .price(784560.37)
                .stock(6)
                .build();

        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(List.of(product, product2));

        List<ProductsEntity> products = productsRepository.findAllByStockGreaterThan(5);

        assertNotNull(products);
        assertEquals(2, products.size());
        verify(jdbcTemplate, times(1)).query(anyString(), any(RowMapper.class));
    }

    @Test
    void findAllByStockGreaterThanNegativeInvalidStock() {
        assertThrows(InvalidDataException.class, () -> productsRepository.findAllByStockGreaterThan(-1));
    }

    @Test
    void findAllByStockGreaterThanNegativeDatabaseError() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenThrow(new DataAccessException("Database error") {});

        assertThrows(DatabaseOperationException.class, () -> productsRepository.findAllByStockGreaterThan(5));
    }

    @Test
    void updateProductNegativeInvalidData() {
        ProductsEntity product = new ProductsEntity();
        product.setProductId(null);

        assertThrows(InvalidDataException.class, () -> productsRepository.updateProduct(product));
    }

    @Test
    void deleteProductByIdNegativeInvalidId() {
        assertThrows(InvalidDataException.class, () -> productsRepository.deleteProductById(0));
    }

    @Test
    void deleteAllNegativeDatabaseError() {
        doThrow(new DataAccessException("Database error") {}).when(jdbcTemplate).update(anyString());

        assertThrows(DatabaseOperationException.class, () -> productsRepository.deleteAll());
    }
}