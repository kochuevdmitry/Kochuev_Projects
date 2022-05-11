package com.example.MyBookShopApp.repositories;

import com.example.MyBookShopApp.data.Authors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;

public interface AuthorsRepository extends JpaRepository<Authors, Integer> {

    @Query("SELECT id, count(id) from Authors group by id")
    Map<String, Integer> findAuthorsAndCount();

    Authors findAuthorsById(Integer authorId);
}
