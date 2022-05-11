package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Authors;
import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.Genre;
import com.example.MyBookShopApp.data.Tags;
import com.example.MyBookShopApp.data.book.review.BookReviewLikeEntity;
import com.example.MyBookShopApp.data.dto.AuthorsDto;
import com.example.MyBookShopApp.data.dto.BookDto;
import com.example.MyBookShopApp.data.dto.UserEntityDto;
import com.example.MyBookShopApp.data.user.UserEntity;
import com.example.MyBookShopApp.repositories.*;
import com.example.MyBookShopApp.service.BookService;
import com.example.MyBookShopApp.service.BookshpCartService;
import com.example.MyBookShopApp.service.BookstoreUserRegisterService;
import com.example.MyBookShopApp.service.GeneralAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class GeneralAdminController extends CommonFooterAndHeaderController {
    private final AuthorsRepository authorsRepository;
    private final TagsRepository tagsRepository;
    private final GenresRepository genresRepository;
    private final GeneralAdminService generalAdminService;
    private final BookReviewLikeRepository bookReviewLikeRepository;
    private final UserEntityRepository userEntityRepository;
    private final BookRepository bookRepository;


    private final Logger logger = Logger.getLogger(GeneralAdminController.class.getName());

    @Autowired
    public GeneralAdminController(BookstoreUserRegisterService userRegister, BookshpCartService bookshpCartService, BookService bookService, AuthorsRepository authorsRepository, TagsRepository tagsRepository, GenresRepository genresRepository, GeneralAdminService generalAdminService, BookReviewLikeRepository bookReviewLikeRepository, UserEntityRepository userEntityRepository, BookRepository bookRepository) {
        super(userRegister, bookshpCartService, bookService);
        this.authorsRepository = authorsRepository;
        this.tagsRepository = tagsRepository;
        this.genresRepository = genresRepository;
        this.generalAdminService = generalAdminService;
        this.bookReviewLikeRepository = bookReviewLikeRepository;
        this.userEntityRepository = userEntityRepository;
        this.bookRepository = bookRepository;
    }

    @ModelAttribute("bookDataToChange")
    public BookDto bookToChange() {
        return new BookDto();
    }

    @ModelAttribute("authorDataToChange")
    public AuthorsDto authorToChange() {
        return new AuthorsDto();
    }

    @ModelAttribute("isAuthorUpdated")
    public Boolean isAuthorUpdated() {
        return false;
    }

    @ModelAttribute("isAuthorUpdateError")
    public Boolean isAuthorUpdateError() {
        return false;
    }

    @ModelAttribute("authorUpdateErrorText")
    public String authorUpdateErrorText() {
        return "";
    }

    @ModelAttribute("bookPubDateOrigin")
    public Date bookPubDateOrigin() {
        return new Date();
    }

    @ModelAttribute("updateBookStatus")
    public Boolean updateBookStatus() {
        return false;
    }

    @ModelAttribute("updateError")
    public Boolean updateError() {
        return false;
    }

    @ModelAttribute("updateBookError")
    public String updateBookError() {
        return "";
    }

    @GetMapping("/genadmin")
    public String genAdmin() {
        return "cms/genadmin";
    }

    @GetMapping("/managebook")
    public String manageBook(Model model) {
        return "cms/managebook";
    }

    @GetMapping("/manageauthor")
    public String manageAuthor(Model model) {
        return "cms/manageauthors";
    }

    @ModelAttribute("reviewDataToConfirm")
    public BookReviewLikeEntity reviewDataToConfirm() {
        return new BookReviewLikeEntity();
    }

    /*@ModelAttribute("userName")
    public UserEntity userName() {
        return new UserEntity();
    }*/

    @ModelAttribute("bookTitle")
    public Book bookTitle() {
        return new Book();
    }

    @GetMapping("/managereviews")
    public String manageReviews(Model model) {
        model.addAttribute("uncheckedReviews", bookReviewLikeRepository.findBookReviewLikeEntitiesByApprovedReview(false));
        return "cms/managereviews";
    }

    @ModelAttribute("users")
    public List<UserEntity> users() {
        return userEntityRepository.findAll();
    }

    @ModelAttribute("userDataToChange")
    public UserEntityDto userDataToChange() {
        UserEntityDto user = new UserEntityDto();
        user.setBanned(false);
        return user;
    }

    @GetMapping("/manageusers")
    public String manageUsers(Model model) {
        return "cms/manageusers";
    }

    @GetMapping("/manageuserbooks")
    public String manageUserBooks(Model model) {
        return "cms/manageuserbooks";
    }

    @GetMapping("/managebook/{slug}")
    public String manageBook(Model model, @PathVariable("slug") String slug) throws ParseException {
        model.addAttribute("books", bookService.getBookData());
        model.addAttribute("updateBookStatus", false);
        model.addAttribute("updateError", false);
        model.addAttribute("updateBookError", "");
        Book book = bookService.getBookBySlug(slug);
        if (book == null) {
            return "cms/managebook";
        }
        if (book.getPubDate() == null) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            Date date = formatter.parse("00.00.0000");
            book.setPubDate(date);
            bookRepository.save(book);
        }
        String date = book.getPubDate().toString().substring(8, 10) + "." + book.getPubDate().toString().substring(5, 7) + "." +
                book.getPubDate().toString().substring(0, 4);
        model.addAttribute("bookPubDateOrigin", date);
        model.addAttribute("bookDataToChange", bookService.getBookBySlug(slug));
        return "cms/managebook";
    }

    @GetMapping("/managebookerr/{slug}")
    public String manageBookErr(Model model, @PathVariable("slug") String slug) throws ParseException {
        model.addAttribute("books", bookService.getBookData());
        Book book = bookService.getBookBySlug(slug);
        if (book.getPubDate() == null) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            Date date = formatter.parse("00.00.0000");
            book.setPubDate(date);
            bookRepository.save(book);
        }
        String date = book.getPubDate().toString().substring(8, 10) + "." + book.getPubDate().toString().substring(5, 7) + "." +
                book.getPubDate().toString().substring(0, 4);
        model.addAttribute("bookPubDateOrigin", date);
        model.addAttribute("bookDataToChange", bookService.getBookBySlug(slug));
        return "cms/managebook";
    }

    @GetMapping("/manageauthor/{id}")
    public String manageAuthor(Model model, @PathVariable("id") Integer id) {
        Authors author = authorsRepository.findAuthorsById(id);
        model.addAttribute("authorDataToChange", author);
        return "cms/manageauthors";
    }

    @GetMapping("/managereviews/{id}")
    public String manageReviews(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("uncheckedReviews", bookReviewLikeRepository.findBookReviewLikeEntitiesByApprovedReview(false));
        BookReviewLikeEntity reviewToConfirm = bookReviewLikeRepository.findBookReviewLikeEntitiesById(id);
        if (reviewToConfirm != null) {
            model.addAttribute("reviewDataToConfirm", reviewToConfirm);
            model.addAttribute("userName", reviewToConfirm.getUser());
            model.addAttribute("bookTitle", bookRepository.findBookById(reviewToConfirm.getBookId()));
        }
        return "cms/managereviews";
    }

    @ModelAttribute("userRegTime")
    public String userRegTime() {
        return "00.00.0000";
    }

    @GetMapping("/manageusers/{id}")
    public String manageUsers(Model model, @PathVariable("id") Integer id) {
        if (id != 0) {
            model.addAttribute("userDataToChange", userEntityRepository.findUserEntityById(id));
            model.addAttribute("userBooks", generalAdminService.getUserBooks(id));
            String date = userEntityRepository.findUserEntityById(id).getRegTime().toString();
            String date1 = date.substring(8, 10) + "." + date.substring(5, 7) + "." +
                    date.substring(0, 4);
            model.addAttribute("userRegTime", date1);
        }
        return "cms/manageusers";
    }

    @GetMapping("/manageuserbooks/{id}")
    public String manageUserBooks(Model model, @PathVariable("id") Integer id) {
        if (id != 0 && id != null) {
            model.addAttribute("userDataToChange", userEntityRepository.findUserEntityById(id));
            model.addAttribute("userBooks", generalAdminService.getUserBooks(id));
        }
        return "cms/manageuserbooks";
    }

    @PostMapping(path = "/managebook/selectbook")
    public String selectBook(@RequestParam("bookSelectSlug") String slug) {
        return "redirect:/admin/managebook/" + slug;
    }

    @PostMapping(path = "/manageauthor/selectauthor")
    public String selectAuthor(@RequestParam("authorSelectId") Integer id) {
        return "redirect:/admin/manageauthor/" + id;
    }

    @PostMapping(path = "/managereviews/selectnext")
    public String selectReview(@RequestParam("reviewSelectId") Integer id) {
        return "redirect:/admin/managereviews/" + id;
    }

    @PostMapping(path = "/manageusers/select")
    public String selectUser(@RequestParam("userSelectId") Integer id) {
        return "redirect:/admin/manageusers/" + id;
    }

    @PostMapping(path = "/manageuserbooks/select")
    public String selectUserBooks(@RequestParam("userSelectId") Integer id) {
        return "redirect:/admin/manageuserbooks/" + id;
    }

    @PostMapping(path = "/managebook/save/{slug}")
    public String updateBook(@PathVariable("slug") String slug, @ModelAttribute("bookDataToChange") BookDto bookDataToChange,
                             @RequestParam("bookPubDate") String pubDate,
                             @RequestParam("authorToChange") Integer authorId,
                             @RequestParam("bookImage") MultipartFile image,
                             @RequestParam("changeImage") Boolean needImageChange,
                             @RequestParam("bookFilePDF") MultipartFile bookFilePDF,
                             @RequestParam("changePDF") Boolean needPDFChange,
                             @RequestParam("bookFileEPUB") MultipartFile bookFileEPUB,
                             @RequestParam("changeEPUB") Boolean needEPUBChange,
                             @RequestParam("bookFileFB2") MultipartFile bookFileFB2,
                             @RequestParam("changeFB2") Boolean needFB2Change,
                             RedirectAttributes redirectAttributes) {
        bookDataToChange.setSlug(slug);


        Book book = generalAdminService.updateBookFields(new Book(), bookDataToChange,  authorId);
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            Date date = formatter.parse(pubDate);
            bookDataToChange.setPubDate(date);
            generalAdminService.updateBookData(bookDataToChange, authorId, image, needImageChange,
                    bookFilePDF, needPDFChange, bookFileEPUB, needEPUBChange, bookFileFB2,
                    needFB2Change);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("updateError", true);
            redirectAttributes.addFlashAttribute("updateBookError", e.getMessage());
            redirectAttributes.addFlashAttribute("bookDataToChange", book);
            logger.info("Book update ERROR: " + e.getMessage());
            return "redirect:/admin/addbook/add";
        }

        return "redirect:/books/" + slug;
    }

    @PostMapping(path = "/manageauthor/save/{id}")
    public String updateAuthor(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes,
                               @ModelAttribute("authorDataToChange") AuthorsDto authorDataToChange,
                               @RequestParam("firstName") String firstName,
                               @RequestParam("lastName") String lastName,
                               @RequestParam("description") String description,
                               @RequestParam("authorImage") MultipartFile authorImage,
                               @RequestParam("changeImage") Boolean needImageChange) {
        authorDataToChange.setId(id);
        try {
            Authors author = generalAdminService.updateAuthorData(authorDataToChange, firstName, lastName, description, authorImage, needImageChange);
            redirectAttributes.addFlashAttribute("authorDataToChange", author);
            redirectAttributes.addFlashAttribute("isAuthorUpdated", true);
            logger.info("AUTHOR UPDATE SUCCESS!!!");
            return "redirect:/authors/slug/" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("isAuthorUpdated", false);
            redirectAttributes.addFlashAttribute("isAuthorUpdateError", true);
            redirectAttributes.addFlashAttribute("authorUpdateErrorText", e.getMessage());
            redirectAttributes.addFlashAttribute("authorDataToChange", authorDataToChange);
            logger.info("Author update ERROR: " + e.getMessage());
            return "redirect:/admin/manageauthors";
        }
    }

    @ModelAttribute("isUserUpdated")
    public Boolean isUserUpdated() {
        return false;
    }

    @ModelAttribute("isUserUpdateFailed")
    public Boolean isUserUpdateFailed() {
        return false;
    }

    @ModelAttribute("userUpdateFailMsg")
    public String userUpdateFailMsg() {
        return "";
    }

    @PostMapping(path = "/manageusers/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @ModelAttribute("userDataToChange") UserEntityDto userDataToChange,
                             @RequestParam("userRegTime") String userRegTime,
                             @RequestParam("changeBan") Boolean toBanOrNotToBan,
                             @RequestParam("newPass1") String newPassword1,
                             @RequestParam("newPass2") String newPassword2,
                             @RequestParam("addAdmin") Boolean addAdmin,
                             RedirectAttributes redirectAttributes) {
        userDataToChange.setId(id);
        userDataToChange.setBanned(toBanOrNotToBan);
        try {
            userDataToChange.setRegTime(LocalDateTime.parse(userRegTime + " 00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
            generalAdminService.updateUser(userDataToChange, newPassword1, newPassword2, addAdmin);
            redirectAttributes.addFlashAttribute("isUserUpdated", true);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("isUserUpdateFailed", true);
            redirectAttributes.addFlashAttribute("userUpdateFailMsg", e.getMessage());
        }
        return "redirect:/admin/manageusers/" + id;
    }

    @ModelAttribute("isUserBooksUpdated")
    public Boolean isUserBooksUpdated() {
        return false;
    }

    @ModelAttribute("isUserBooksUpdatedError")
    public Boolean isUserBooksUpdatedError() {
        return false;
    }

    @ModelAttribute("isUserBooksUpdatedErrorText")
    public String isUserBooksUpdatedErrorText() {
        return "";
    }

    @PostMapping(path = "/manageuserbooks/update/{id}")
    public String updateUserBooks(@PathVariable("id") Integer id,
                                  @RequestParam("bookAddToUser") Integer bookAddToUser,
                                  RedirectAttributes redirectAttributes) {

        try {
            if (id != null && id != 0 && bookAddToUser != 0) {
                generalAdminService.updateUserBooks(bookAddToUser, id);
                redirectAttributes.addFlashAttribute("isUserBooksUpdated", true);
                return "redirect:/admin/manageuserbooks/" + id;
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("isUserBooksUpdatedError", true);
            redirectAttributes.addFlashAttribute("isUserBooksUpdatedErrorText", "Ошибка: " + e.getMessage());
        }
        return "redirect:/admin/manageuserbooks";
    }

    @ModelAttribute("isReviewConfirmed")
    public Boolean isReviewConfirmed() {
        return false;
    }

    @ModelAttribute("isReviewDeleted")
    public Boolean isReviewDeleted() {
        return false;
    }

    @PostMapping(path = "/managereviews/confirm/{id}")
    public String confirmReview(@PathVariable("id") Integer id,
                                @RequestParam("confirmReview") Boolean confirmReview,
                                RedirectAttributes redirectAttributes) {
        BookReviewLikeEntity review = bookReviewLikeRepository.findBookReviewLikeEntitiesById(id);
        if (confirmReview && review != null) {
            review.setApprovedReview(true);
            bookReviewLikeRepository.save(review);
            redirectAttributes.addFlashAttribute("isReviewConfirmed", true);
        } else {
            if (review != null) {
                bookReviewLikeRepository.delete(review);
                redirectAttributes.addFlashAttribute("isReviewDeleted", true);
            }
        }
        return "redirect:/admin/managereviews";
    }

    @ModelAttribute("books")
    public List<Book> currentBooks() {
        return bookService.getBookData();
    }

    @ModelAttribute("authors")
    public List<Authors> currentAuthors() {
        return authorsRepository.findAll();
    }

    @ModelAttribute("tags")
    public List<Tags> currentTags() {
        return tagsRepository.findAll().stream().distinct().collect(Collectors.toList());
    }

    @ModelAttribute("genres")
    public List<Genre> currentGenres() {
        return genresRepository.findAll().stream().distinct().collect(Collectors.toList());
    }

    @GetMapping("/addbook")
    public String bookToAddAdmin(Model model) {
        model.addAttribute("addingStatus", true);
        model.addAttribute("addingError", "");
        return "cms/addbook";
    }

    @PostMapping(path = "/addbook/add")
    public String addNewBookToDB(@RequestParam("bookTitle") String bookTitle,
                                 @RequestParam("bookPubDate") String bookPubDate,
                                 @RequestParam("bookDescription") String bookDescription,
                                 @RequestParam("bookPrice") Double bookPrice,
                                 @RequestParam("bookDiscount") Double bookDiscount,
                                 @RequestParam("bookAuthorId") Integer bookAuthorId,
                                 @RequestParam("bookImage") MultipartFile bookImage,
                                 @RequestParam("bookFilePDF") MultipartFile bookFilePDF,
                                 @RequestParam("bookFileEPUB") MultipartFile bookFileEpub,
                                 @RequestParam("bookFileFB2") MultipartFile bookFileFB2,
                                 Model model) {
        Book book = new Book();
        try {
            book.setTitle(bookTitle);
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            book.setPubDate(formatter.parse(bookPubDate));
            book.setSlug(generalAdminService.slugForNewBook());
            book.setDescription(bookDescription);
            book.setPriceOld(bookPrice.intValue());
            book.setPrice(bookDiscount);

            if (authorsRepository.findAuthorsById(bookAuthorId) != null) {
                book.setAuthor(authorsRepository.findAuthorsById(bookAuthorId));
            } else {
                logger.info("Adding book ERROR " + book.getSlug());
                model.addAttribute("addingStatus", false);
                model.addAttribute("addingError", "Введите автора!");
                return "cms/addbook";
            }

            book.setRating(0.0);
            book.setPopularity(0.0);
            generalAdminService.saveNewBook(book, bookImage, bookFilePDF, bookFileEpub, bookFileFB2);
            logger.info("addedNewBookToDB " + book.getSlug());
            return "redirect:/admin/manageBookGenres?bookId=" + book.getId();

        } catch (Exception e) {
            logger.info("Adding book ERROR " + book.getSlug());
            e.printStackTrace();
            model.addAttribute("addingStatus", false);
            model.addAttribute("addingError", e.getMessage());
            return "cms/addbook";
        }
    }

    @ModelAttribute("isBookRemovedFromUser")
    public Boolean isBookRemovedFromUser() {
        return false;
    }

    @ModelAttribute("isBookRemovedFromUserError")
    public Boolean isBookRemovedFromUserError() {
        return false;
    }

    @ModelAttribute("thisBookWasRemovedNotice")
    public String thisBookWasRemovedNotice() {
        return "";
    }

    @ModelAttribute("isBookRemovedFromUserErrorNotice")
    public String isBookRemovedFromUserErrorNotice() {
        return "";
    }

    @GetMapping("/manageuserbooksremove")
    public String manageUserBooksRemove(@RequestParam("bookId") Integer bookId,
                                        @RequestParam("userId") Integer userId,
                                        RedirectAttributes redirectAttributes) {
        try {
            logger.info("manageuserbooksremove " + bookId + " " + userId);
            String bookTitle = generalAdminService.removeUserBook(bookId, userId);
            redirectAttributes.addFlashAttribute("isBookRemovedFromUser", true);
            redirectAttributes.addFlashAttribute("thisBookWasRemovedNotice", "Book: " + bookTitle + " was removed!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("isBookRemovedFromUserError", true);
            redirectAttributes.addFlashAttribute("isBookRemovedFromUserErrorNotice", "Ошибка удаления книги у юзера: " + e.getMessage());
        }
        redirectAttributes.addFlashAttribute("userDataToChange", userEntityRepository.findUserEntityById(userId));
        redirectAttributes.addFlashAttribute("userBooks", generalAdminService.getUserBooks(userId));
        return "redirect:/admin/manageuserbooks";
    }

    @ModelAttribute("bookToUpdate")
    public Book bookToUpdate() {
        return new Book();
    }

    @GetMapping(value = {"/manageBookGenres", "/manageBookGenres/{bookId}"})
    public String addGenreToBook(@RequestParam(value = "bookId", required = false) String bookId,
                                 Model model) {
        if (bookId != null) {
            if (!bookId.equals("null")) {
                try {
                    model.addAttribute("bookToUpdate", bookRepository.findBookById(Integer.valueOf(bookId)));
                    return "/cms/manageBookGenres";
                } catch (Exception e) {
                    return "redirect:/admin/managebook";
                }
            }
        }
        return "redirect:/admin/managebook";
    }

    @GetMapping("/spontaneous_refactoring")
    public String spontaneousRefactoring() {
        //bookService.spontaneousRefactoring();
        return "redirect:/cms/genadmin";
    }

    @ModelAttribute("statusGenreAdd")
    public String statusGenreAdd() {
        return "";
    }

    @PostMapping("/manageBookGenresAdd/{bookId}")
    public String manageBookGenresAdd(@PathVariable(value = "bookId", required = false) Integer bookId,
                                      @RequestParam(value = "genreSelectId", required = false) Integer genreId,
                                      RedirectAttributes redirectAttributes) {
        if (bookId != null && genreId != 0) {
            if (generalAdminService.addGenreToBook(bookId, genreId)) {
                redirectAttributes.addFlashAttribute("statusGenreAdd", "added");
            } else {
                redirectAttributes.addFlashAttribute("statusGenreAdd", "no added genre");
            }
        } else {
            redirectAttributes.addFlashAttribute("statusGenreAdd", "no added genre");
        }
        return "redirect:/admin/manageBookGenres?bookId=" + bookId;
    }

    @GetMapping("/manageBookGenresListToAddRemove")
    public String manageBookGenresListToAddRemove(@RequestParam(value = "bookId", required = false) Integer bookId,
                                                  @RequestParam(value = "genresId", required = false) Integer genresId,
                                                  RedirectAttributes redirectAttributes) {
        generalAdminService.removeGenreFromBook(bookId, genresId);
        redirectAttributes.addFlashAttribute("statusGenreAdd", "genre removed");
        return "redirect:/admin/manageBookGenres?bookId=" + bookId;
    }

    @ModelAttribute("statusTagAdd")
    public String statusTagAdd() {
        return "";
    }

    @GetMapping(value = {"/manageBookTags", "/manageBookTags/{bookId}"})
    public String addTagsToBook(@RequestParam(value = "bookId", required = false) String bookId,
                                Model model) {
        if (bookId != null) {
            if (!bookId.equals("null")) {
                try {
                    model.addAttribute("bookToUpdate", bookRepository.findBookById(Integer.valueOf(bookId)));
                    return "/cms/manageBookTags";
                } catch (Exception e) {
                    return "redirect:/admin/managebook";
                }
            }
        }
        return "redirect:/admin/managebook";
    }

    @PostMapping("/manageBookTagsAdd/{bookId}")
    public String manageBookTagsAdd(@PathVariable(value = "bookId", required = false) Integer bookId,
                                    @RequestParam(value = "tagSelectId", required = false) Integer tagId,
                                    RedirectAttributes redirectAttributes) {
        if (bookId != null && tagId != 0) {
            if (generalAdminService.addTagToBook(bookId, tagId)) {
                redirectAttributes.addFlashAttribute("statusTagAdd", "added");
            } else {
                redirectAttributes.addFlashAttribute("statusTagAdd", "no added tag");
            }
        } else {
            redirectAttributes.addFlashAttribute("statusTagAdd", "no added tag");
        }
        return "redirect:/admin/manageBookTags?bookId=" + bookId;
    }

    @GetMapping("/manageBookTagsListToAddRemove")
    public String manageBookTagsListToAddRemove(@RequestParam(value = "bookId", required = false) Integer bookId,
                                                @RequestParam(value = "tagId", required = false) Integer tagId,
                                                RedirectAttributes redirectAttributes) {
        generalAdminService.removeTagFromBook(bookId, tagId);
        redirectAttributes.addFlashAttribute("statusTagAdd", "tag removed");
        return "redirect:/admin/manageBookTags?bookId=" + bookId;
    }

}

