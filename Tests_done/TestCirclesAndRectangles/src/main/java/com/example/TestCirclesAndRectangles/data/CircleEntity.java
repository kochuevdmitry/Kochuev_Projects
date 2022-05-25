package com.example.TestCirclesAndRectangles.data;

import com.example.TestCirclesAndRectangles.data.enums.ColorEnum;

import javax.persistence.*;

@Entity
@Table(name="circles")
public class CircleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "radius")
    private Double radius;
    
    @Column(name = "color")
    private ColorEnum color;

    public Double getCircleArea(){
        return Math.PI * Math.pow(radius,2);
    }

    public Double getCircleLength(){
        return 2 * Math.PI * radius;
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
}
