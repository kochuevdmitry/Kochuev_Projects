package com.example.TestCirclesAndRectangles.service;

import com.example.TestCirclesAndRectangles.data.CircleEntity;
import com.example.TestCirclesAndRectangles.data.RectangleEntity;
import com.example.TestCirclesAndRectangles.data.dto.CircleDto;
import com.example.TestCirclesAndRectangles.data.dto.RectangleDto;
import com.example.TestCirclesAndRectangles.data.enums.ColorEnum;
import com.example.TestCirclesAndRectangles.repository.CirclesRepository;
import com.example.TestCirclesAndRectangles.repository.RectanglesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FiguresService {

    private CirclesRepository circlesRepository;
    private RectanglesRepository rectanglesRepository;

    @Autowired
    public FiguresService(CirclesRepository circlesRepository, RectanglesRepository rectanglesRepository) {
        this.circlesRepository = circlesRepository;
        this.rectanglesRepository = rectanglesRepository;
    }

    public List<CircleDto> getSortedRadiusCircles() {
        List<CircleEntity> circlesSortedEntities = circlesRepository.getAllOrderByRadiusDesc();
        List<CircleDto> circleDtoList = new ArrayList<>();
        for (CircleEntity circle : circlesSortedEntities) {
            circleDtoList.add(getCircleDto(circle));
        }
        return circleDtoList;
    }

    private CircleDto getCircleDto(CircleEntity circle) {
        return new CircleDto(circle.getId(), circle.getRadius(),
                circle.getColor(), circle.getCircleArea(), circle.getCircleLength());

    }

    public List<RectangleDto> getSortedDigonalRectangles() {
        List<RectangleEntity> rectangleEntities = rectanglesRepository.findAll();
        List<RectangleDto> rectangleDtoList = new ArrayList<>();
        for (RectangleEntity rectangle : rectangleEntities) {
            rectangleDtoList.add(getRectangleDto(rectangle));
        }
        return rectangleDtoList.stream().sorted(Comparator.comparing(RectangleDto::getDiagonal)).collect(Collectors.toList());
    }

    private RectangleDto getRectangleDto(RectangleEntity rectangle) {
        return new RectangleDto(rectangle.getId(), rectangle.getSide1(), rectangle.getSide2(),
                rectangle.getColor(), rectangle.getRectangleArea(), rectangle.getPerimeter(), rectangle.getDiagonal());
    }

    public List<Object> showByColorOrAllFigures(String color) {
        List<Object> figuresList = new ArrayList<>();
        if (color == null) {
            figuresList.addAll(circlesRepository.findAll());
            figuresList.addAll(rectanglesRepository.findAll());
        } else  {
          figuresList.addAll(circlesRepository.findAllByColor(ColorEnum.getColorEnumByString(color)));
          figuresList.addAll(rectanglesRepository.findAllByColor(ColorEnum.getColorEnumByString(color)));
        }
        return figuresList;
    }
}
