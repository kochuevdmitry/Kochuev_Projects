package com.example.MyBookShopApp.repositories;

import com.example.MyBookShopApp.data.user.UserEntity;
import com.example.MyBookShopApp.data.user.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Integer> {

    UserRoleEntity findUserRoleEntityByUserForRole(UserEntity user);

    UserRoleEntity findUserRoleEntityByUserIdAndUserRole(int userId, String userRoleString);

    UserRoleEntity findUserRoleEntityByUserId(int userId);


}
