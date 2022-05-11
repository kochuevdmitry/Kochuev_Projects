package com.example.MyBookShopApp.helpers;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.dto.BookDto;

public class Helpers {
    public static class BookHelper {
        public static BookDto bookToBookDto(Book book) {
            BookDto bookDto = new BookDto();
            bookDto.setId(book.getId());
            bookDto.setAuthor(book.getAuthor().getName());
            bookDto.setIsBestseller(book.getIsBestseller());
            bookDto.setSlug(book.getSlug());
            bookDto.setImage(book.getImage());
            bookDto.setDiscount(book.discount());
            bookDto.setTitle(book.getTitle());
            bookDto.setDiscountPrice(book.discountPrice());
            bookDto.setPriceOld(book.getPriceOld());
            return bookDto;
        }
    }
}