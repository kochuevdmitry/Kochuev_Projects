package com.example.TestCirclesAndRectangles.controllers;

import com.example.TestCirclesAndRectangles.data.dto.CircleDto;
import com.example.TestCirclesAndRectangles.data.dto.RectangleDto;
import com.example.TestCirclesAndRectangles.service.FiguresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CirclesRectanglesColorController {

    private final FiguresService figuresService;

    @Autowired
    public CirclesRectanglesColorController(FiguresService figuresService) {
        this.figuresService = figuresService;
    }

    @GetMapping("/circles")
    public List<CircleDto> showSortedRadiusCircles() {
        return figuresService.getSortedRadiusCircles();
    }

    @GetMapping("/rectangles")
    public List<RectangleDto> showSortedDiagonalRectangles() {
        return figuresService.getSortedDigonalRectangles();
    }

    @GetMapping("/figures")
    public List<Object> showByColorOrAllFigures(@RequestParam(name = "c", required = false) String color) {
        return figuresService.showByColorOrAllFigures(color);
    }
}
