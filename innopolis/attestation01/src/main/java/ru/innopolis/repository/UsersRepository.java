package ru.innopolis.repository;

import ru.innopolis.entity.UsersEntity;

import java.util.List;

public interface UsersRepository {

    List<UsersEntity> findAll();
}
