package repository;

import entity.UsersEntity;

import java.util.List;

public interface UsersRepository {

    UsersEntity saveUser(UsersEntity userEntity);

    UsersEntity findById(int id);

    List<UsersEntity> findAll();

    void updateUser(UsersEntity userEntity);

    void deleteUserById (int id);

    void deleteAll();
}
