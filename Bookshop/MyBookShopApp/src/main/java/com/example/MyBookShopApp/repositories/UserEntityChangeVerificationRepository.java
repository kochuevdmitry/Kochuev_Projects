package com.example.MyBookShopApp.repositories;

import com.example.MyBookShopApp.data.user.UserEntityChangeVerification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityChangeVerificationRepository extends JpaRepository<UserEntityChangeVerification, Integer> {

    UserEntityChangeVerification findUserEntityChangeVerificationByToken(String token);

}
