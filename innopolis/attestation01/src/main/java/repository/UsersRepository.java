package repository;

import entity.UsersEntity;

import java.util.List;

public interface UsersRepository {

    List<UsersEntity> findAll();
}
