package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.Genre;
import com.example.MyBookShopApp.data.dto.BookDto;
import com.example.MyBookShopApp.data.dto.GenresDto;
import com.example.MyBookShopApp.helpers.Helpers;
import com.example.MyBookShopApp.repositories.BookRepository;
import com.example.MyBookShopApp.repositories.GenresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenreService {

    private GenresRepository genresRepository;
    private BookRepository bookRepository;

    @Autowired
    public GenreService(GenresRepository genresRepository, BookRepository bookRepository) {
        this.genresRepository = genresRepository;
        this.bookRepository = bookRepository;
    }

    public List<BookDto> getBooksByGenreId(Integer genreId, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        List<Book> bookList = bookRepository.findBooksByGenreId(genreId, nextPage);
        List<BookDto> bookDtos = new ArrayList<>();
        if (bookList != null) {
            for (Book book : bookList) {
                bookDtos.add(Helpers.BookHelper.bookToBookDto(book));
            }
        }
        return bookDtos;
    }

    public Genre getGenreById(Integer genreId) {
        return genresRepository.findGenreById(genreId);
    }


    //собираем список всех топовых жанров и углубляемся в другой метод рекурсией
    public List<GenresDto> getTopGenresList() {
        List<Genre> topGenres = genresRepository.findGenreEntitiesByParentIdIsNull();
        List<Genre> allGenres = genresRepository.findAll();
        List<GenresDto> topGenresDtoList = new ArrayList<>();

        GenresDto currentGenresDto;
        for (Genre nextTopGenre : topGenres) {
            //пробегаемся вниз по детям, заполняя сортированный список
            currentGenresDto = new GenresDto();
            currentGenresDto.setGenre(nextTopGenre);
            currentGenresDto.setOffset(0);
                topGenresDtoList.addAll(getAllChildrenForParent(currentGenresDto, 0, allGenres));
        }
        updateLastLineChildrenList(topGenresDtoList);
        setOnlyLastOneInLineWhichHasFewInLine(topGenresDtoList);
        return topGenresDtoList;
    }

    //тут по готовому рекурсивному списку пробегаем и устанавливаем параметры LastInLine и NotLastLine для последующего вывода на сайт в нужной визуальной структуре по заданию
    private void setOnlyLastOneInLineWhichHasFewInLine(List<GenresDto> list) {
        for (GenresDto genresDto : list) {
            //please keep the sequence 1-2-3
            genresDto = sortGenresStep1(genresDto);
            genresDto = sortGenresStep2(genresDto);
            genresDto = sortGenresStep3(genresDto);
            if (genresDto.getGenresDtoChildrenList() != null) {
                setOnlyLastOneInLineWhichHasFewInLine(genresDto.getGenresDtoChildrenList());
            }
        }
    }

    private GenresDto sortGenresStep1(GenresDto genresDto) {
        if (genresDto.getLastLineChildrenList() != null && !genresDto.isHasChildren() && !genresDto.isLastInLine() && genresDto.getLastLineChildrenList().size() > 1) {
                genresDto.setNotLastLine(false);
        }
        return genresDto;
    }

    private GenresDto sortGenresStep2(GenresDto genresDto) {
        if (genresDto.getLastLineChildrenList() != null && !genresDto.isHasChildren() && genresDto.isLastInLine() && genresDto.getLastLineChildrenList().size() > 1) {
                Boolean checkIsLastLine = false;
                for (int i = genresDto.getLastLineChildrenList().size() - 2; i >= 0; i--) {
                    if (!genresDto.getLastLineChildrenList().get(i).isHasChildren()) {
                        checkIsLastLine = true;
                    }
                }
                if (genresDto.getLastLineChildrenList().get(genresDto.getLastLineChildrenList().size() - 2).isHasChildren() && !checkIsLastLine) {
                    genresDto.setLastInLine(false);
                }
        }
        return genresDto;
    }

    private GenresDto sortGenresStep3(GenresDto genresDto) {
        if (genresDto.isLastInLine() && genresDto.isHasChildren()) {
            genresDto.setNotLastLine(true);
            genresDto.setLastInLine(false);
        }
        if (genresDto.isLastInLine() && !genresDto.isHasChildren()) {
            genresDto.setNotLastLine(true);
        }
        if (genresDto.getLastLineChildrenList() != null && genresDto.isLastInLine() && genresDto.getLastLineChildrenList().size() > 1) {
                genresDto.setNotLastLine(false);
        }
        return genresDto;
    }

    private void updateLastLineChildrenList(List<GenresDto> topGenresDtoList) {
        for (GenresDto genresDto : topGenresDtoList) {
            if (genresDto.isHasChildren() == false && genresDto.getParentGenresDto() != null) {
                    checkAndUpdateLastInLineTrue(genresDto);
            }
        }
    }

    private void checkAndUpdateLastInLineTrue(GenresDto genresDto) {
        if (genresDto.getParentGenresDto().getChildNoKidsLastOneInLine() == genresDto.getGenre().getId()) {
            genresDto.setLastInLine(true);
            List<GenresDto> brothersAndSisters = new ArrayList<>();
            for (GenresDto brothersAndSisterSuka : genresDto.getParentGenresDto().getGenresDtoChildrenList()) {
                if (!brothersAndSisterSuka.isHasChildren()) {
                    brothersAndSisters.add(brothersAndSisterSuka);
                }
            }
            genresDto.setLastLineChildrenList(brothersAndSisters);
        }
    }

    //пробегаемся вниз по детям, заполняя сортированный список, тоже углубляясь рекурсией
    private List<GenresDto> getAllChildrenForParent(GenresDto currGenresDto, Integer
            offset, List<Genre> allGenres) {
        GenresDto childGenresDto = new GenresDto();
        Genre currentGenre = currGenresDto.getGenre();
        GenresDto currentGenresDto = currGenresDto;
        List<GenresDto> topGenresDtoList = new ArrayList<>();
        Integer offseting = offset;

        List<GenresDto> childrenList = new ArrayList<>();

        offseting++;
        for (Genre childGenresCheck : allGenres) {
            try {
                if (childGenresCheck.getParentId() == currentGenre.getId()) {
                    childGenresDto = new GenresDto();
                    childGenresDto.setOffset(offseting);
                    childGenresDto.setParentGenresDto(currentGenresDto);
                    childGenresDto.setGenre(childGenresCheck);
                    childGenresDto.setLastLineChildrenList(childrenList);
                    currentGenresDto.setHasChildren(true);
                    childrenList.add(childGenresDto);
                }
            } catch (NullPointerException nullPointerException) {
                nullPointerException.getMessage();
            }
        }

        if (!childGenresDto.isHasChildren()) {
            childGenresDto.setLastInLine(true);
        }

        for (GenresDto nextGenresDtoChild : childrenList) {
            getAllChildrenForParent(nextGenresDtoChild, offseting, allGenres);
        }

        currGenresDto.setGenresDtoChildrenList(childrenList);
        if (currGenresDto.getParentGenresDto() == null) {
            topGenresDtoList.add(currentGenresDto);
        }
        return topGenresDtoList;
    }

    public List<Genre> getGenresList() {
        return genresRepository.findAll();
    }
}
