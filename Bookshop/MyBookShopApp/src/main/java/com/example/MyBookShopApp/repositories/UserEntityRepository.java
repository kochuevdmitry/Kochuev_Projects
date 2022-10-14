package com.example.MyBookShopApp.repositories;

import com.example.MyBookShopApp.data.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findUserEntityById(int id);

}
