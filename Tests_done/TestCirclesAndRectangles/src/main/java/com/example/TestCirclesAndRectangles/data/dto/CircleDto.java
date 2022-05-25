package com.example.TestCirclesAndRectangles.data.dto;

import com.example.TestCirclesAndRectangles.data.enums.ColorEnum;

public class CircleDto {
    private Long id;
    private Double radius;
    private ColorEnum color;
    private Double area;
    private Double length;

    public CircleDto(Long id, Double radius, ColorEnum color, Double area, Double length) {
        this.id = id;
        this.radius = radius;
        this.color = color;
        this.area = area;
        this.length = length;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
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

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }
}
