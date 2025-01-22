package repository;

import entity.ProductsEntity;

import java.util.List;

public interface ProductsRepository {

    List<ProductsEntity>findAll();
}
