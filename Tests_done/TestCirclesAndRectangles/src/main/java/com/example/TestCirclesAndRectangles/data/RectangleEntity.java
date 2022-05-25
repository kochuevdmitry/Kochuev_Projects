package com.example.TestCirclesAndRectangles.data;

import com.example.TestCirclesAndRectangles.data.enums.ColorEnum;

import javax.persistence.*;

@Entity
@Table(name = "rectangles")
public class RectangleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "side1")
    private Double side1;

    @Column(name = "side2")
    private Double side2;

    @Column(name = "color")
    private ColorEnum color;

    public Double getRectangleArea() {
        return side1 * side2;
    }

    public Double getPerimeter() {
        return 2 * (side1 + side2);
    }

    public Double getDiagonal() {
        return Math.sqrt(Math.pow(side1, 2) + Math.pow(side2, 2));
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
}
