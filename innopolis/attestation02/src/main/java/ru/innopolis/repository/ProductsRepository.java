package ru.innopolis.repository;

import ru.innopolis.entity.ProductsEntity;

import java.util.List;

public interface ProductsRepository {

    ProductsEntity saveProduct(ProductsEntity productsEntity);

    ProductsEntity findById (int id);

    List<ProductsEntity>findAll();

    void updateProduct(ProductsEntity productEntity);

    List<ProductsEntity> findAllSortedByPrice();

    List<ProductsEntity> findAllByStockGreaterThan(int stock);

    void deleteProductById(int id);

    void deleteAll();
}
