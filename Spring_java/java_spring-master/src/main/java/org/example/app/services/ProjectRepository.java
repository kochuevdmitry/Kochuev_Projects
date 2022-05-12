package org.example.app.services;

import org.example.web.dto.BookRegex;

import java.util.List;

public interface ProjectRepository<T> {

    List<T> getAll();

    List<T> getFilteredBooks(BookRegex book);

    void store(T book);

    boolean removeItemById(Integer bookIdToRemove);

    boolean removeItemByRegex(BookRegex book);
}
