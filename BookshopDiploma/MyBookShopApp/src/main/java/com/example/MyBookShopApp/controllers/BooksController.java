package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.Tags;
import com.example.MyBookShopApp.data.book.review.BookReviewLikeEntity;
import com.example.MyBookShopApp.data.dto.BookReviewLikeEntityDto;
import com.example.MyBookShopApp.data.dto.CartDto;
import com.example.MyBookShopApp.data.dto.ReviewLikesDto;
import com.example.MyBookShopApp.data.user.UserEntity;
import com.example.MyBookShopApp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Logger;


@Controller
@RequestMapping("/books")
public class BooksController extends CommonFooterAndHeaderController {

    private final BookReviewLikeService bookReviewLikeService;
    private final ResourceStorage storage;
    private final Book2UserViewHistoryService book2UserViewHistoryService;

    private final Logger logger = Logger.getLogger(BooksController.class.getName());

    @Autowired
    public BooksController(BookshpCartService bookshpCartService, BookService bookService, BookReviewLikeService bookReviewLikeService, ResourceStorage storage, BookstoreUserRegisterService userRegister, Book2UserViewHistoryService book2UserViewHistoryService) {
        super(userRegister, bookshpCartService, bookService);
        this.bookReviewLikeService = bookReviewLikeService;
        this.storage = storage;
        this.book2UserViewHistoryService = book2UserViewHistoryService;
    }

    @GetMapping("/{slug}")
    public String bookPage(@PathVariable("slug") String slug, Model model) throws Exception {

        logger.info("BooksController bookPage " + slug);
        Book book = bookService.getBookBySlug(slug);
        List<BookReviewLikeEntity> bookReviewLikeEntities = bookReviewLikeService.getBookLikesByBookId(book.getId());
        TreeMap<String, Integer> ratings = bookReviewLikeService.getBookRatingsByBookId(book.getId());

        model.addAttribute("slugBook", book);
        model.addAttribute("bookReviews", bookReviewLikeEntities);
        model.addAttribute("bookRatings", ratings);
        model.addAttribute("bookTags", book.getTagsList());
        model.addAttribute("bookStatus", 0);

        if (bookService.checkAuthorization(model, userRegister)) {
            model.addAttribute("bookStatus", bookService.getBookPaidStatus(book.getId()));
            book2UserViewHistoryService.addBookUserViewTransaction(book);
            bookService.updatePopularity(book.getId());
            return "/books/slugmy";
        }

        bookService.updatePopularity(book.getId());
        return "/books/slug";
    }

    @ModelAttribute(name = "bookTags")
    public List<Tags> bookTags() {
        return new ArrayList<>();
    }

    @PostMapping("/{slug}/img/save")
    public String saveNewBoookImage(@RequestParam("file") MultipartFile file, @PathVariable("slug") String slug) throws IOException {

        String savePath = storage.saveNewBookImage(file, slug);
        Book bookToUpdate = bookService.getBookBySlug(slug);
        bookToUpdate.setImage(savePath);
        bookService.bookSave(bookToUpdate); //save new path in db here

        return ("redirect:/books/" + slug);
    }

    @GetMapping("/download/{hash}")
    public ResponseEntity<ByteArrayResource> bookFile(@PathVariable("hash") String hash) throws IOException {

        Path path = storage.getBookFilePath(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book file path: " + path);

        MediaType mediaType = storage.getBookFileMime(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book file mime type: " + mediaType);

        byte[] data = storage.getBookFileByteArray(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book file data len: " + data.length);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
                .contentType(mediaType)
                .contentLength(data.length)
                .body(new ByteArrayResource(data));
    }

    @ModelAttribute("addReview")
    public BookReviewLikeEntityDto addReview() {
        return new BookReviewLikeEntityDto();
    }

    @ModelAttribute("isReviewSent")
    public Boolean isReviewSent() {
        return false;
    }
    @ModelAttribute("isReviewFailed")
    public Boolean isReviewFailed() {
        return false;
    }

    @PostMapping("/bookReview/{slug}")
    public String ratingAdd(@PathVariable("slug") String slug,
                            @ModelAttribute BookReviewLikeEntityDto addReview,
                            RedirectAttributes redirectAttributes) {

        if(addReview.getValue()==0){
            redirectAttributes.addFlashAttribute("addReview", addReview);
            redirectAttributes.addFlashAttribute("isReviewFailed", true);
            return "redirect:/books/" + slug;
        }

        logger.info("BooksController ratingAdd " + slug + " " + addReview.getValue());
        addReview.setApprovedReview(false);

        try {
            UserEntity user = (UserEntity) userRegister.getCurrentUser();
            Book book = bookService.getBookBySlug(slug);

            bookReviewLikeService.addReviewLikeEntity(user, addReview, book);
            bookService.updateBookRating(book.getId());

            logger.info("ratingAdded " + addReview.getText());
            redirectAttributes.addFlashAttribute("isReviewSent", true);
        } catch (Exception e) {
            logger.info("ratingAdd " + e);
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("isReviewSent", false);
        }
        return "redirect:/books/" + slug;
    }

    @PostMapping("/rateBookReview")
    public String ratingAssess(@RequestBody ReviewLikesDto reviewLikesDto) {
        String slug;
        try {
            UserEntity user = (UserEntity) userRegister.getCurrentUser();
            slug = bookReviewLikeService.setLikeDislikeForReview(reviewLikesDto.value, reviewLikesDto.reviewid, user);
            return "redirect:/books/" + slug;
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/books/" + bookReviewLikeService.getBookSlugByReviewId(reviewLikesDto.reviewid);
        }
    }

}
