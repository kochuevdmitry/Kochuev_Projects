package com.example.MyBookShopApp.data.dto;

import com.example.MyBookShopApp.data.Genre;

import java.util.List;

public class GenresDto {
    private int offset;
    private Genre genre;
    private GenresDto parentGenresDto;
    private List<GenresDto> genresDtoChildrenList;
    private Boolean hasChildren = false;
    private Integer childNoKidsLastOneInLine;
    private Boolean lastInLine = false;
    private List<GenresDto> lastLineChildrenList;
    private Boolean notLastLine = true;

    public GenresDto() {
    }

    public List<GenresDto> getGenresDtoChildrenList() {
        return genresDtoChildrenList;
    }

    public void setGenresDtoChildrenList(List<GenresDto> genresDtoChildrenList) {
        this.genresDtoChildrenList = genresDtoChildrenList;
    }

    public GenresDto getParentGenresDto() {
        return parentGenresDto;
    }

    public void setParentGenresDto(GenresDto parentGenresDto) {
        this.parentGenresDto = parentGenresDto;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Boolean isLastInLine() {
        return lastInLine;
    }

    public void setLastInLine(Boolean lastInLine) {
        this.lastInLine = lastInLine;
    }

    public Integer getChildNoKidsLastOneInLine() {
        return childNoKidsLastOneInLine;
    }

    public List<GenresDto> getLastLineChildrenList() {
        return lastLineChildrenList;
    }

    public void setLastLineChildrenList(List<GenresDto> lastLineChildrenList) {
        this.lastLineChildrenList = lastLineChildrenList;
    }

    public Boolean isNotLastLine() {
        return notLastLine;
    }

    public void setNotLastLine(Boolean notLastLine) {
        this.notLastLine = notLastLine;
    }
}
