package com.example.MyBookShopApp.repositories;

import com.example.MyBookShopApp.data.book.file.BookFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookFileRepository extends JpaRepository<BookFile, Integer> {

    BookFile findBookFileByHash(String hash);

    BookFile findBookFileByPath(String path);

    BookFile findBookFileByTypeIdAndBookId(Integer typeId, Integer bookId);
}
