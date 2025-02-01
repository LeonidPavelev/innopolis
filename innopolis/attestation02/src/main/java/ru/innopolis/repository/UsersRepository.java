package ru.innopolis.repository;

import ru.innopolis.entity.UsersEntity;

import java.util.List;

public interface UsersRepository {

    UsersEntity saveUser(UsersEntity userEntity);

    UsersEntity findById(int id);

    List<UsersEntity> findAll();

    List<UsersEntity> findAllSortedByFirstName();

    List<UsersEntity> findAllSortedByLastName();

    void updateUser(UsersEntity userEntity);

    void deleteUserById (int id);

    void deleteAll();
}
