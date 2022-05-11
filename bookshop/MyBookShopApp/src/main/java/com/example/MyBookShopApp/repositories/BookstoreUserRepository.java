package com.example.MyBookShopApp.repositories;

import com.example.MyBookShopApp.data.user.UserEntity;
import com.example.MyBookShopApp.data.user.UserEntityChangeVerification;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookstoreUserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findUserEntityByEmail(String email);

    UserEntity findUserEntityByPhone(String phone);

    UserEntity findUserEntityById(Integer id);
}
