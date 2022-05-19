package com.example.test1.repository;

import com.example.test1.data.RequestsHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RequestHistoryRepository extends JpaRepository<RequestsHistoryEntity, Integer> {

}
