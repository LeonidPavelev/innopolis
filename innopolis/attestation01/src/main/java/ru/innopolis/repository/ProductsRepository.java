package ru.innopolis.repository;

import ru.innopolis.entity.ProductsEntity;

import java.util.List;

public interface ProductsRepository {

    List<ProductsEntity>findAll();
}
