package com.example.MyBookShopApp.repositories;

import com.example.MyBookShopApp.data.book.links.Book2UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Book2UserRepository extends JpaRepository<Book2UserEntity, Integer> {

    Book2UserEntity findBook2UserEntityByBookIdAndUserId(Integer bookId, Integer userId);

    List<Book2UserEntity> findAllByUserIdAndTypeId(Integer userId, Integer typeId);

    Integer countAllByBookIdAndTypeId(Integer bookId, Integer typeId);

    List<Book2UserEntity> findAllByUserId(Integer id);
}
