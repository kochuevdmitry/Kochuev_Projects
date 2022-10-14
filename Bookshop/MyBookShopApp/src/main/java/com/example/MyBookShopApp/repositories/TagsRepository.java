package com.example.MyBookShopApp.repositories;

import com.example.MyBookShopApp.data.Tags;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagsRepository extends JpaRepository<Tags, Integer> {

    Tags findTagsById(Integer tagId);
}
