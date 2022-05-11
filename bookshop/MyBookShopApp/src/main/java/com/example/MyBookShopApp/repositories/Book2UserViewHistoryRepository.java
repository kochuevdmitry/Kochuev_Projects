package com.example.MyBookShopApp.repositories;

import com.example.MyBookShopApp.data.book.links.Book2UserViewHistoryEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface Book2UserViewHistoryRepository extends JpaRepository<Book2UserViewHistoryEntity, Integer> {

    List<Book2UserViewHistoryEntity> findAll();

    List<Book2UserViewHistoryEntity> findAllByUserId(Integer userId);

    List<Book2UserViewHistoryEntity> findAllByBookId(Integer bookId);

    List<Book2UserViewHistoryEntity> findBook2UserViewHistoryEntitiesByBookId(Integer bookId);

    List<Book2UserViewHistoryEntity> findAllByBookIdAndTimeAfter(Integer bookId, LocalDateTime dateAfter);

    List<Book2UserViewHistoryEntity> findBook2UserViewHistoriesByTimeAfter(LocalDateTime dateAfter);

    @Query(value = "SELECT book_id FROM book_view_history bvh WHERE bvh.user_id = ?1 GROUP BY bvh.book_id",
            countQuery = "SELECT count(book_id) FROM book_view_history",
            nativeQuery = true)
    List<Integer> findBookIdsByUserIdDistinct(Integer userId, Pageable nextPage);


}
