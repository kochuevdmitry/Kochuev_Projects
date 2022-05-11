package com.example.MyBookShopApp.repositories;

import com.example.MyBookShopApp.data.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenresRepository extends JpaRepository<Genre, Integer> {
    List<Genre> findGenreEntitiesByParentIdIsNull();

    Genre findGenreById(Integer genreId);
}
