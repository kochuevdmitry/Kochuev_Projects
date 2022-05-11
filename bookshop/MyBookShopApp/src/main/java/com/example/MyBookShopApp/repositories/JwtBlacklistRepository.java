package com.example.MyBookShopApp.repositories;

import com.example.MyBookShopApp.data.JwtBlacklistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtBlacklistRepository extends JpaRepository<JwtBlacklistEntity, String> {

    JwtBlacklistEntity findJwtBlacklistEntityByJwtBlacklistedToken(String token);

}
