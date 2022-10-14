package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.data.Authors;
import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.Genre;
import com.example.MyBookShopApp.data.Tags;
import com.example.MyBookShopApp.data.book.links.Book2GenreEntity;
import com.example.MyBookShopApp.data.book.links.Book2UserEntity;
import com.example.MyBookShopApp.data.book.review.BookReviewLikeEntity;
import com.example.MyBookShopApp.data.dto.BookDto;
import com.example.MyBookShopApp.data.google.api.books.Item;
import com.example.MyBookShopApp.data.google.api.books.Root;
import com.example.MyBookShopApp.data.user.UserEntity;
import com.example.MyBookShopApp.data.user.UserRoleEntity;
import com.example.MyBookShopApp.errs.BookstoreApiWrongParameterException;
import com.example.MyBookShopApp.helpers.Helpers;
import com.example.MyBookShopApp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {

    private BookRepository bookRepository;
    private RestTemplate restTemplate;
    private Book2UserRepository book2UserRepository;
    private Book2UserViewHistoryRepository book2UserViewHistoryRepository;
    private BookReviewLikeRepository bookReviewLikeRepository;
    private BookstoreUserRegisterService userRegister;
    private UserRoleRepository userRoleRepository;
    private TagsRepository tagsRepository;


    @Value("${google.books.api.key}")
    private String apiKey;

    @Autowired
    public BookService(BookRepository bookRepository, RestTemplate restTemplate, Book2UserRepository book2UserRepository, Book2UserViewHistoryRepository book2UserViewHistoryRepository, BookReviewLikeRepository bookReviewLikeRepository, BookstoreUserRegisterService userRegister, UserRoleRepository userRoleRepository, TagsRepository tagsRepository, Book2GenreRepository book2GenreRepository) {
        this.bookRepository = bookRepository;
        this.restTemplate = restTemplate;
        this.book2UserRepository = book2UserRepository;
        this.book2UserViewHistoryRepository = book2UserViewHistoryRepository;
        this.bookReviewLikeRepository = bookReviewLikeRepository;
        this.userRegister = userRegister;
        this.userRoleRepository = userRoleRepository;
        this.tagsRepository = tagsRepository;
    }

    public List<Book> getBookData() {
        return bookRepository.findAll();
    }

    public void bookSave(Book book) {
        bookRepository.save(book);
    }

    public List<Book> getBooksByAuthor(String authorName) {
        return bookRepository.findBooksByAuthorFirstNameContaining(authorName);
    }

    public Book getBookBySlug(String slug) {
        return bookRepository.findBookBySlug(slug);
    }

    public List<Book> getBooksByTitle(String title) throws BookstoreApiWrongParameterException {
        if (title.equals("") || title.length() <= 1) {
            throw new BookstoreApiWrongParameterException("Wrong values passed to one or more parameters");
        } else {
            List<Book> data = bookRepository.findBooksByTitleContaining(title);
            if (data.size() > 0) {
                return data;
            } else {
                throw new BookstoreApiWrongParameterException("No data found with specified parameters...");
            }
        }
    }

    public List<Book> getBooksWithPriceBetween(Integer min, Integer max) {
        return bookRepository.findBooksByPriceOldBetween(min, max);
    }

    public List<Book> getBooksWithPrice(Integer price) {
        return bookRepository.findBooksByPriceOldIs(price);
    }

    public List<Book> getBooksWithMaxPrice() {
        return bookRepository.getBooksWithMaxDiscount();
    }

    public List<Book> getBestsellers() {
        return bookRepository.getBestsellers();
    }

    public List<BookDto> getPageOfRecommendedBooks(Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset * limit, offset * limit + limit);
        List<Book> bookList = bookRepository.findAllOrderByRating(nextPage);

        List<BookDto> bookDtos = new ArrayList<>();
        if (bookList != null) {
            for (Book book : bookList) {
                bookDtos.add(Helpers.BookHelper.bookToBookDto(book));
            }
        }
        return bookDtos;
    }

    public List<BookDto> getPageOfRecommendedBooksForUser(Integer offset, Integer limit) {
        UserEntity user = null;
        List<Book2UserEntity> paidBooks2User = null;
        try {
            user = (UserEntity) userRegister.getCurrentUser();
            paidBooks2User = book2UserRepository.findAllByUserIdAndTypeId(user.getId(), 3);
        }catch (Exception e) {
            e.printStackTrace();
        }

        List<Book> paidUserBooks = new ArrayList<>();
        List<Book> byAuthorsBooks = new ArrayList<>();
        List<Book> byTagsBooks = new ArrayList<>();
        List<Book> byGenresBooks = new ArrayList<>();
        Book book;
        if (paidBooks2User != null) {
            //подбираем рекомендации по авторам, жанрам, тегам + новизне
            for (Book2UserEntity book2User : paidBooks2User) {
                book = bookRepository.findBookById(book2User.getBookId());
                paidUserBooks.add(book);
                byAuthorsBooks = byAuthorsDistinctSorted(book);
                byTagsBooks = byTagsDistinctSort(book);
                byGenresBooks = byGenresBooksDistictSorted(book);
            }
            List<BookDto> combinedRecommendations = getCombinedRecommendationsList(byAuthorsBooks, byGenresBooks, byTagsBooks, user);
            combinedRecommendations = combinedRecommendations.stream().distinct().collect(Collectors.toList());
            List<BookDto> pagedBooks;
            if (offset * limit + limit <= combinedRecommendations.size()) {
                pagedBooks = combinedRecommendations.subList(offset * limit, offset * limit + limit);
            } else {
                pagedBooks = combinedRecommendations.subList(offset * limit, combinedRecommendations.size());
            }
            return pagedBooks;
        }
        //иначе выводим рекомендации по рейтингу
        Pageable nextPage = PageRequest.of(offset, limit);
        return getBookDtosByRating(nextPage);
    }

    private List<Book> byGenresBooksDistictSorted(Book book){
        List<Book> byGenresBooks = new ArrayList<>();
        for (Genre genre : book.getGenre()) {
            byGenresBooks.addAll(genre.getBooksList());
        }
        byGenresBooks = byGenresBooks.stream().distinct().collect(Collectors.toList());
        Collections.sort(byGenresBooks, new Comparator<Book>() {
            @Override
            public int compare(Book lhs, Book rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                try {
                    return lhs.getPubDate().compareTo(rhs.getPubDate());
                } catch (Exception e) {
                    if (lhs.getPubDate() == null) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            }
        });
        return byGenresBooks;
    }

    private List<Book> byAuthorsDistinctSorted(Book book) {
        List<Book> byAuthorsBooks = new ArrayList<>();
        byAuthorsBooks.addAll(bookRepository.findBooksByAuthorId(book.getAuthor().getId()));
        byAuthorsBooks = byAuthorsBooks.stream().distinct().collect(Collectors.toList());
        Collections.sort(byAuthorsBooks, new Comparator<Book>() {
            @Override
            public int compare(Book lhs, Book rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                try {
                    return lhs.getPubDate().compareTo(rhs.getPubDate());
                } catch (Exception e) {
                    if (lhs.getPubDate() == null) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            }
        });
        return byAuthorsBooks;
    }

    private List<Book> byTagsDistinctSort(Book book){
        List<Book> byTagsDistinctSort =new ArrayList<>();
        for (Tags tag : book.getTagsList()) {
            byTagsDistinctSort.addAll(tag.getBookList());
        }
        byTagsDistinctSort = byTagsDistinctSort.stream().distinct().collect(Collectors.toList());
        Collections.sort(byTagsDistinctSort, new Comparator<Book>() {
            @Override
            public int compare(Book lhs, Book rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending

                try {
                    return lhs.getPubDate().compareTo(rhs.getPubDate());
                } catch (Exception e) {
                    if (lhs.getPubDate() == null) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            }
        });
        return byTagsDistinctSort;
    }

    private List<BookDto> getBookDtosByRating(Pageable nextPage) {
        List<Book> bookList = bookRepository.findAllOrderByRating(nextPage);
        List<BookDto> bookDtos = new ArrayList<>();
        if (bookList != null) {
            for (Book book : bookList) {
                bookDtos.add(Helpers.BookHelper.bookToBookDto(book));
            }
        }
        return bookDtos;
    }

    //намешиваем список рекомендаций
    public List<BookDto> getCombinedRecommendationsList(List<Book> byAuthorsBooks, List<Book> byGenresBooks, List<Book> byTagsBooks, UserEntity user) {
        Integer maxSize = byAuthorsBooks.size() > byTagsBooks.size() ? byAuthorsBooks.size() : byTagsBooks.size();
        maxSize = maxSize > byGenresBooks.size() ? maxSize : byGenresBooks.size();

        List<BookDto> combinedRecommendations = new ArrayList<>();
        for (int i = 0; i < maxSize; i++) {
            if (byAuthorsBooks.size() > i) {
                combinedRecommendations.add(Helpers.BookHelper.bookToBookDto(byAuthorsBooks.get(i)));
            }
            if (byTagsBooks.size() > i) {
                combinedRecommendations.add(Helpers.BookHelper.bookToBookDto(byTagsBooks.get(i)));
            }
            if (byGenresBooks.size() > i) {
                combinedRecommendations.add(Helpers.BookHelper.bookToBookDto(byGenresBooks.get(i)));
            }
        }
        for (int i = 0; i < combinedRecommendations.size(); i++) {
            if (book2UserRepository.findBook2UserEntityByBookIdAndUserId
                    (combinedRecommendations.get(i).getId(), user.getId()) != null) {
                combinedRecommendations.remove(i);
            }
        }
        return combinedRecommendations;
    }

    //search with database
    public Page<Book> getPageOfSearchResultBooks(String searchWord, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findBookByTitleContaining(searchWord, nextPage);
    }

    //search through the google books api
    public List<BookDto> getPageOfGoogleBooksApiSearchResult(String searchWord, Integer offset, Integer limit) {
        String REQUEST_URL = "https://www.googleapis.com/books/v1/volumes" +
                "?q=" + searchWord +
                "&key=" + apiKey +
                "&filter=paid-ebooks" +
                "&startIndex=" + offset +
                "&maxResults=" + limit;

        Root root = restTemplate.getForEntity(REQUEST_URL, Root.class).getBody();
        ArrayList<BookDto> list = new ArrayList<>();
        if (root != null) {
            for (Item item : root.getItems()) {
                Book book = new Book();
                if (item.getVolumeInfo() != null) {
                    book.setAuthor(new Authors(item.getVolumeInfo().getAuthors()));
                    book.setTitle(item.getVolumeInfo().getTitle());
                    book.setImage(item.getVolumeInfo().getImageLinks().getThumbnail());
                }
                if (item.getSaleInfo() != null) {
                    book.setPrice(item.getSaleInfo().getRetailPrice().getAmount());
                    Double oldPrice = item.getSaleInfo().getListPrice().getAmount();
                    book.setPriceOld(oldPrice.intValue());
                }
                list.add(Helpers.BookHelper.bookToBookDto(book));
            }
        }
        return list;
    }

    public Integer getCountOfSearchResultBooks(String searchWord) {
        return bookRepository.countBooksByTitleContaining(searchWord);
    }

    public List<BookDto> getListOfRecentBooks(Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        List<Book> bookList = bookRepository.findAllRecentBooks(nextPage);

        List<BookDto> bookDtos = new ArrayList<>();
        if (bookList != null) {
            for (Book book : bookList) {
                bookDtos.add(Helpers.BookHelper.bookToBookDto(book));
            }
        }
        return bookDtos;
    }

    public List<BookDto> getListOfPopularBooks(Integer offset, Integer limit) {
        List<Book> bookList = bookRepository.findAllOrderByPopularity();
        bookList = bookList.stream().distinct().collect(Collectors.toList());
        ;
        Integer start = offset * limit;
        Integer end = offset * limit + limit;
        Integer size = bookList.size();
        if (start < size) {
            if (end < size) {
                bookList = bookList.subList(start, end);
            } else {
                bookList = bookList.subList(start, size);
            }
        } else {
            bookList = new ArrayList<>();
        }

        List<BookDto> bookDtos = new ArrayList<>();
        if (bookList != null) {
            for (Book book : bookList) {
                bookDtos.add(Helpers.BookHelper.bookToBookDto(book));
            }
        }
        return bookDtos;
    }

    public List<BookDto> getPageOfRecentBooks(Date from, Date to, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        List<Book> bookList = bookRepository.findBooksByPubDateBetweenOrderByPubDate(from, to, nextPage).getContent();
        List<BookDto> bookDtos = new ArrayList<>();
        if (bookList != null) {
            for (Book book : bookList) {
                bookDtos.add(Helpers.BookHelper.bookToBookDto(book));
            }
        }
        return bookDtos;
    }

    public HashMap<Tags, Integer> getMapOfTags() {
        HashMap<Tags, Integer> tagsMap = new HashMap<>();
        List<Tags> tagsList = tagsRepository.findAll().stream().distinct().collect(Collectors.toList());
        for (Tags tag : tagsList) {
            tagsMap.put(tag, tag.getBookList().size());
        }
        return tagsMap;
    }

    public List<BookDto> getListOfTaggedBooks(Integer offset, Integer limit, Integer tagId) {
        List<Book> bookList = tagsRepository.findTagsById(tagId).getBookList();

        if (bookList.size() >= (offset == 0 ? 20 : (offset + 1) * limit)) {
            bookList = bookList.subList(offset == 0 ? 0 : limit * (offset), offset == 0 ? 20 : (offset + 1) * limit);
        } else {
            try {
                bookList = bookList.subList(offset == 0 ? 0 : limit * offset, bookList.size());
            } catch (Exception e) {
                bookList = new ArrayList<>();
            }
        }

        List<BookDto> bookDtos = new ArrayList<>();
        if (bookList != null) {
            for (Book book : bookList) {
                bookDtos.add(Helpers.BookHelper.bookToBookDto(book));
            }
        }
        return bookDtos;
    }

    public Boolean checkAuthorization(Model model, BookstoreUserRegisterService userRegister) throws Exception {
        Authentication authenticationCheck = SecurityContextHolder.getContext().getAuthentication();
        if (authenticationCheck != null && !authenticationCheck.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ANONYMOUS"))) {
            model.addAttribute("currUsr", userRegister.getCurrentUser());
            return true;
        }
        return false;
    }

    public void updatePopularity(Integer bookId) {
        Book book = bookRepository.findBookById(bookId);
        Integer b = book2UserRepository.countAllByBookIdAndTypeId(bookId, 3) == null ? 0 : book2UserRepository.countAllByBookIdAndTypeId(bookId, 3);
        Integer c = book2UserRepository.countAllByBookIdAndTypeId(bookId, 2) == null ? 0 : book2UserRepository.countAllByBookIdAndTypeId(bookId, 2);
        Integer k = book2UserRepository.countAllByBookIdAndTypeId(bookId, 3) == null ? 0 : book2UserRepository.countAllByBookIdAndTypeId(bookId, 1);
        //LocalDateTime monthBack = LocalDateTime.now().minusMonths(1);
        Integer v = book2UserViewHistoryRepository.findBook2UserViewHistoryEntitiesByBookId(bookId).size();// book2UserViewHistoryRepository.findAllByBookIdAndTimeAfter(bookId, monthBack).size();
        Double popularity = b + 0.7 * c + 0.4 * k + 0.1 * v;
        book.setPopularity(popularity);
        bookRepository.save(book);
    }

    public void updateBookRating(Integer bookId) {
        Book book = bookRepository.findBookById(bookId);
        List<BookReviewLikeEntity> bookReviewLikeEntities
                = bookReviewLikeRepository.findBookReviewLikeEntitiesByBookId(book.getId());
        Double rating = 0.0;
        if (bookReviewLikeEntities != null && bookReviewLikeEntities.size() != 0) {
            for (BookReviewLikeEntity like : bookReviewLikeEntities) {
                rating += like.getValue();
            }
            rating = rating / bookReviewLikeEntities.size();
            book.setRating(rating);
            bookRepository.save(book);
        }
    }

    public List<BookDto> getLastViewedBooksPage(Integer offset, Integer limit) throws Exception {
        UserEntity user = (UserEntity) userRegister.getCurrentUser();
        if (user != null) {
            Pageable nextPage = PageRequest.of(offset, limit);
            List<Integer> bookIds = book2UserViewHistoryRepository.findBookIdsByUserIdDistinct(user.getId(), nextPage);
            List<BookDto> bookDtos = new ArrayList<>();
            if (bookIds != null) {
                for (Integer bookId : bookIds) {
                    bookDtos.add(Helpers.BookHelper.bookToBookDto(bookRepository.findBookById(bookId)));
                }
                return bookDtos;
            }
            return bookDtos;
        }
        return new ArrayList<>();
    }

    public Integer getBookPaidStatus(Integer bookId) throws Exception {
        UserEntity user = (UserEntity) userRegister.getCurrentUser();
        if (book2UserRepository.findBook2UserEntityByBookIdAndUserId(bookId, user.getId()) != null) {
            return book2UserRepository.findBook2UserEntityByBookIdAndUserId(bookId, user.getId()).getTypeId();
        }
        return 0;
    }

    public boolean checkAdmin(BookstoreUserRegisterService userRegister) throws Exception {
        UserEntity user = (UserEntity) userRegister.getCurrentUser();
        List<UserRoleEntity> userRoles = user.getUserRoleEntities();
        if (userRoles != null){
            for (UserRoleEntity userRole: userRoles){
                String admin = userRole.getUserRole();
                if (admin.equals("ROLE_ADMIN")) {
                    return true;
                }
            }
        }
        return false;
    }

    public void spontaneousRefactoring() {
        List<Book> allBooks = bookRepository.findAll();
        List<Tags> allTags = tagsRepository.findAll();
        for (Book book : allBooks) {
            List<Tags> tagsForBook = new ArrayList<>();

            int countThree = 0;
            for (Tags tag : allTags) {

                if (Math.random() > 0.5 && countThree < 3) {
                    tagsForBook.add(tag);
                    countThree++;
                }
            }

            book.setTagsList(tagsForBook);
            bookRepository.save(book);
        }
    }


}
