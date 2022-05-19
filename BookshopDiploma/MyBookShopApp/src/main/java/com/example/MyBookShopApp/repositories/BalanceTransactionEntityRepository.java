package com.example.MyBookShopApp.repositories;

import com.example.MyBookShopApp.data.payments.BalanceTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BalanceTransactionEntityRepository extends JpaRepository<BalanceTransactionEntity, Double> {

    List<BalanceTransactionEntity> findAllByUserId(Integer userId);
}
