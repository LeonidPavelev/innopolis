package repository;

import entity.ProductsEntity;

import java.util.List;

public interface ProductsRepository {

    ProductsEntity saveProduct(ProductsEntity productsEntity);

    ProductsEntity findById (int id);

    List<ProductsEntity>findAll();

    void updateProduct(ProductsEntity productEntity);

    void deleteProductById(int id);

    void deleteAll();
}
