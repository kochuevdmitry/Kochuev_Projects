package com.example.product_list.repository;

import com.example.product_list.entity.ListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListRepository extends JpaRepository<ListEntity, Long> {

    ListEntity findByName(String name);

    List<ListEntity> findAllById(Long id);
}
