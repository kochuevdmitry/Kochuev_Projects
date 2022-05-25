package com.example.TestCirclesAndRectangles.data.dto;

import com.example.TestCirclesAndRectangles.data.enums.ColorEnum;

public class RectangleDto {
    private Long id;
    private Double side1;
    private Double side2;
    private ColorEnum color;
    private Double area;
    private Double perimeter;
    private Double diagonal;

    public RectangleDto(Long id, Double side1, Double side2, ColorEnum color, Double area, Double perimeter, Double diagonal) {
        this.id = id;
        this.side1 = side1;
        this.side2 = side2;
        this.color = color;
        this.area = area;
        this.perimeter = perimeter;
        this.diagonal = diagonal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getSide1() {
        return side1;
    }

    public void setSide1(Double side1) {
        this.side1 = side1;
    }

    public Double getSide2() {
        return side2;
    }

    public void setSide2(Double side2) {
        this.side2 = side2;
    }

    public ColorEnum getColor() {
        return color;
    }

    public void setColor(ColorEnum color) {
        this.color = color;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Double getPerimeter() {
        return perimeter;
    }

    public void setPerimeter(Double perimeter) {
        this.perimeter = perimeter;
    }

    public Double getDiagonal() {
        return diagonal;
    }

    public void setDiagonal(Double diagonal) {
        this.diagonal = diagonal;
    }
}
