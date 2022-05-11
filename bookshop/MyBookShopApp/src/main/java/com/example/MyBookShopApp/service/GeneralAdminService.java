package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.data.Authors;
import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.Tags;
import com.example.MyBookShopApp.data.book.file.BookFile;
import com.example.MyBookShopApp.data.book.file.BookFileType;
import com.example.MyBookShopApp.data.book.links.Book2GenreEntity;
import com.example.MyBookShopApp.data.book.links.Book2TagEntity;
import com.example.MyBookShopApp.data.book.links.Book2UserEntity;
import com.example.MyBookShopApp.data.book.links.Book2UserTypeEnum;
import com.example.MyBookShopApp.data.dto.AuthorsDto;
import com.example.MyBookShopApp.data.dto.BookDto;
import com.example.MyBookShopApp.data.dto.UserEntityDto;
import com.example.MyBookShopApp.data.user.UserEntity;
import com.example.MyBookShopApp.data.user.UserRoleEntity;
import com.example.MyBookShopApp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
public class GeneralAdminService {

    private BookRepository bookRepository;
    private ResourceStorage storage;
    private BookFileRepository bookFileRepository;
    private AuthorsRepository authorsRepository;
    private Book2UserRepository book2UserRepository;
    private UserEntityRepository userEntityRepository;
    private PasswordEncoder passwordEncoder;
    private Book2GenreRepository book2GenreRepository;
    private GenresRepository genresRepository;
    private Book2TagRepository book2TagRepository;
    private TagsRepository tagsRepository;
    private UserRoleRepository userRoleRepository;

    @Autowired
    public GeneralAdminService(BookRepository bookRepository, ResourceStorage storage, BookFileRepository bookFileRepository, GenresRepository genresRepository, AuthorsRepository authorsRepository, Book2UserRepository book2UserRepository, UserEntityRepository userEntityRepository, PasswordEncoder passwordEncoder, Book2GenreRepository book2GenreRepository, GenresRepository genresRepository1, Book2TagRepository book2TagRepository, TagsRepository tagsRepository, UserRoleRepository userRoleRepository) {
        this.bookRepository = bookRepository;
        this.storage = storage;
        this.bookFileRepository = bookFileRepository;
        this.authorsRepository = authorsRepository;
        this.book2UserRepository = book2UserRepository;
        this.userEntityRepository = userEntityRepository;
        this.passwordEncoder = passwordEncoder;
        this.book2GenreRepository = book2GenreRepository;
        this.genresRepository = genresRepository1;
        this.book2TagRepository = book2TagRepository;
        this.tagsRepository = tagsRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public String slugForNewBook() {
        String slug = "book-"
                + (char) ((int) (Math.random() * (25)) + 97)
                + (char) ((int) (Math.random() * (25)) + 97)
                + (char) ((int) (Math.random() * (25)) + 97)
                + "-"
                + (char) ((int) (Math.random() * (25)) + 97)
                + (char) ((int) (Math.random() * (25)) + 97)
                + (char) ((int) (Math.random() * (25)) + 97);

        if (bookRepository.findBookBySlug(slug) != null) {
            slugForNewBook();
        }

        return slug;
    }

    public String saveNewBook(Book book, MultipartFile bookImage, MultipartFile bookFilePDF,
                              MultipartFile bookFileEpub, MultipartFile bookFileFB2) throws IOException {
        book.setImage(storage.saveNewBookImage(bookImage, book.getSlug()));
        bookRepository.save(book);

        if (!bookFilePDF.isEmpty()) {
            saveFile(bookFilePDF, book, BookFileType.PDF);
        }
        if (!bookFileEpub.isEmpty()) {
            saveFile(bookFileEpub, book, BookFileType.EPUB);
        }
        if (!bookFileFB2.isEmpty()) {
            saveFile(bookFileFB2, book, BookFileType.FB2);
        }
        return book.getSlug();
    }

    private void saveFile(MultipartFile bookFileSave, Book book, BookFileType bookFileType) throws IOException {
        BookFile bookFile;
        if (bookFileRepository.findBookFileByTypeIdAndBookId(BookFileType.getTypeIdByString(bookFileType), book.getId()) != null) {
            bookFile = bookFileRepository.findBookFileByTypeIdAndBookId(BookFileType.getTypeIdByString(bookFileType), book.getId());
        } else {
            bookFile = new BookFile();
            bookFile.setBook(book);
            bookFile.setTypeId(BookFileType.getTypeIdByString(bookFileType));
        }
        bookFile.setPath(storage.saveNewBookFile(bookFileSave, book.getSlug(), bookFileType));
        bookFile.setHash("hash" + (book.getTitle() + bookFileType + LocalDateTime.now()).hashCode());
        bookFileRepository.save(bookFile);
    }


    public Book updateBookData(BookDto bookDataToChange, Integer authorId, MultipartFile image,
                               Boolean needImageChange, MultipartFile bookFilePDF, Boolean needPDFChange,
                               MultipartFile bookFileEPUB, Boolean needEPUBChange,
                               MultipartFile bookFileFB2, Boolean needFB2Change) {

        Book bookToChange = updateBookFields(bookRepository.findBookBySlug(bookDataToChange.getSlug()), bookDataToChange, authorId);

        if (needImageChange) {
            if (!image.isEmpty()) {
                try {
                    bookToChange.setImage(storage.saveNewBookImage(image, bookToChange.getSlug()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                storage.deleteBookImageFromStorage(bookToChange.getImage());
                bookToChange.setImage("");
            }
        }

        try {
            updateBookFile(bookFilePDF, needPDFChange, BookFileType.PDF, bookToChange);
            updateBookFile(bookFileEPUB, needEPUBChange, BookFileType.EPUB, bookToChange);
            updateBookFile(bookFileFB2, needFB2Change, BookFileType.FB2, bookToChange);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bookRepository.save(bookToChange);

        return bookToChange;
    }

    public void updateBookFile(MultipartFile file, Boolean needChange, BookFileType fileType, Book book) throws IOException {
        if (needChange) {
            if (!file.isEmpty()) {
                saveFile(file, book, fileType);
            } else {
                storage.deleteBookFileFromStorageAndDB(book.getSlug(), fileType);
            }
        }
    }

    public Book updateBookFields(Book bookToChange, BookDto bookDataToChange, Integer bookAuthorId) {
        bookToChange.setTitle(bookDataToChange.getTitle());
        bookToChange.setDescription(bookDataToChange.getDescription());
        bookToChange.setPubDate(bookDataToChange.getPubDate());
        bookToChange.setPrice(bookDataToChange.getPrice());
        bookToChange.setPriceOld(bookDataToChange.getPriceOld());
        bookToChange.setAuthor(authorsRepository.findAuthorsById(bookAuthorId));
        return bookToChange;
    }

    public Authors updateAuthorData(AuthorsDto authorDataToChange, String firstName, String lastName,
                                    String description, MultipartFile authorImage, Boolean needImageChange) throws IOException {
        Authors authorToChange = updateAuthorsFields(authorsRepository.findAuthorsById(authorDataToChange.getId())
                , firstName, lastName, description);

        if (needImageChange) {
            if (!authorImage.isEmpty()) {
                authorToChange.setPhoto(storage.saveNewAuthorImage(authorImage, authorToChange.getSlug()));
            } else {
                storage.deleteAuthorImageFromStorage(authorToChange.getPhoto());
                authorToChange.setPhoto("");
            }
        }
        authorsRepository.save(authorToChange);
        return authorToChange;
    }

    public Authors updateAuthorsFields(Authors authorToChange, String firstName, String lastName, String description) {
        authorToChange.setFirstName(firstName);
        authorToChange.setLastName(lastName);
        authorToChange.setDescription(description);
        return authorToChange;
    }

    public HashMap<Book, String> getUserBooks(Integer id) {
        HashMap<Book, String> userBooks = new HashMap<>();
        List<Book2UserEntity> book2UserEntityList = book2UserRepository.findAllByUserId(id);
        for (Book2UserEntity book2User : book2UserEntityList) {
            userBooks.put(bookRepository.findBookById(book2User.getBookId()), Book2UserTypeEnum.getTypeByTypeId(book2User.getTypeId()));
        }
        return userBooks;
    }

    public void updateUser(UserEntityDto userDataToChange, String newPassword1, String newPassword2, Boolean addAdmin) {
        UserEntity user = userEntityRepository.findUserEntityById(userDataToChange.getId());
        user = updateUserDtoToUser(user, userDataToChange);
        if (newPassword1 != null && newPassword2 != null && newPassword1.equals(newPassword2)) {
                user.setPassword(passwordEncoder.encode(newPassword1));
        }
        userEntityRepository.save(user);
        if (addAdmin) {
            UserRoleEntity userRole = userRoleRepository.findUserRoleEntityByUserIdAndUserRole(user.getId(), "ROLE_ADMIN");
            if (userRole == null) {
                userRole = new UserRoleEntity();
                userRole.setUserId(user.getId());
                userRole.setUserRole("ROLE_ADMIN");
                userRole.setUserForRole(user);
                userRoleRepository.save(userRole);
            }
        }
    }

    private UserEntity updateUserDtoToUser(UserEntity user, UserEntityDto userEntityDto) {
        user.setBanned(userEntityDto.getBanned());
        user.setRegTime(userEntityDto.getRegTime());
        user.setBalance(userEntityDto.getBalance());
        user.setPhone(userEntityDto.getPhone());
        user.setEmail(userEntityDto.getEmail());
        user.setHash(userEntityDto.getHash());
        user.setName(userEntityDto.getName());
        user.setVerified(userEntityDto.getVerified());
        return user;
    }

    public void updateUserBooks(Integer bookAddToUserId, Integer userId) {
        Book2UserEntity book2User = book2UserRepository.findBook2UserEntityByBookIdAndUserId(bookAddToUserId, userId);
        if (book2User == null) {
            book2User = new Book2UserEntity();
        }
        book2User.setUserId(userId);
        book2User.setBookId(bookAddToUserId);
        book2User.setTypeId(3);
        book2User.setTime(LocalDateTime.now());
        book2UserRepository.save(book2User);
    }

    public String removeUserBook(Integer bookId, Integer userId) {
        Book2UserEntity book2User = book2UserRepository.findBook2UserEntityByBookIdAndUserId(bookId, userId);
        book2UserRepository.delete(book2User);
        return bookRepository.findBookById(bookId).getTitle();
    }

    public Boolean addGenreToBook(Integer bookId, Integer genreId) {
        Book2GenreEntity book2Genre = book2GenreRepository.findBook2GenreEntityByBookIdAndGenreId(bookId, genreId);
        if (book2Genre == null) {
            Book book = bookRepository.findBookById(bookId);
            book.getGenre().add(genresRepository.findGenreById(genreId));
            bookRepository.save(book);
            return true;
        }
        return false;
    }


    public void removeGenreFromBook(Integer bookId, Integer genresId) {
        try {
            Book2GenreEntity book2Genre = book2GenreRepository.findBook2GenreEntityByBookIdAndGenreId(bookId, genresId);
            book2GenreRepository.delete(book2Genre);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Boolean addTagToBook(Integer bookId, Integer tagId) {
        Book2TagEntity book2TagEntity = book2TagRepository.findBook2TagByBookIdAndTagId(bookId, tagId);
        if (book2TagEntity == null) {
            Book book = bookRepository.findBookById(bookId);
            List<Tags> tagsList = book.getTagsList();
            tagsList.add(tagsRepository.findTagsById(tagId));
            book.setTagsList(tagsList);
            bookRepository.save(book);
            return true;
        }
        return false;
    }

    public void removeTagFromBook(Integer bookId, Integer tagId) {
        try {
            Book2TagEntity book2TagEntity = book2TagRepository.findBook2TagByBookIdAndTagId(bookId, tagId);
            book2TagRepository.delete(book2TagEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
