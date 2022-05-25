package com.example.TestCirclesAndRectangles.repository;

import com.example.TestCirclesAndRectangles.data.RectangleEntity;
import com.example.TestCirclesAndRectangles.data.enums.ColorEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RectanglesRepository extends JpaRepository<RectangleEntity, Integer> {

    List<RectangleEntity> findAllByColor(ColorEnum color);
}
