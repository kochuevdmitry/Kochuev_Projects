package com.example.TestCirclesAndRectangles.repository;

import com.example.TestCirclesAndRectangles.data.CircleEntity;
import com.example.TestCirclesAndRectangles.data.enums.ColorEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface CirclesRepository extends JpaRepository<CircleEntity, Integer> {

    @Query(value = "SELECT * FROM circles ORDER BY circles.radius",
            nativeQuery = true)
    List<CircleEntity> getAllOrderByRadiusDesc();

    List<CircleEntity> findAllByColor(ColorEnum color);
}
