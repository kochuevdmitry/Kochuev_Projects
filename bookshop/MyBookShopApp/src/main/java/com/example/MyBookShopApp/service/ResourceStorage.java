package com.example.MyBookShopApp.service;


import com.example.MyBookShopApp.data.book.file.BookFile;
import com.example.MyBookShopApp.data.book.file.BookFileType;
import com.example.MyBookShopApp.repositories.BookFileRepository;
import liquibase.util.file.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.logging.Logger;

@Service
public class ResourceStorage {

    private final BookFileRepository bookFileRepository;
    @Value("${upload.path}")
    String uploadPath;
    @Value("${download.path}")
    String downloadPath;
    @Value("${authorsPhoto.path}")
    String authorsPhotoPath;

    @Autowired
    public ResourceStorage(BookFileRepository bookFileRepository) {
        this.bookFileRepository = bookFileRepository;
    }

    public String saveNewBookImage(MultipartFile file, String slug) throws IOException {

        String resourceURI = null;
        Logger.getLogger(this.getClass().getSimpleName()).info("image upload start");

        if (!file.isEmpty()) {
            if (!new File(uploadPath).exists()) {
                Files.createDirectories(Paths.get(uploadPath));
                Logger.getLogger(this.getClass().getSimpleName()).info("created image folder in " + uploadPath);
            }

            String fileName = slug + "." + FilenameUtils.getExtension(file.getOriginalFilename());
            Path path = Paths.get(uploadPath, fileName);
            resourceURI = "/book-covers/" + fileName;
            file.transferTo(path); //uploading user file here
            Logger.getLogger(this.getClass().getSimpleName()).info(fileName + "book image uploaded OK!");
        }

        return resourceURI;
    }

    public String saveNewBookFile(MultipartFile file, String slug, BookFileType bookFileType) throws IOException {
        String resourceURI = null;
        Logger.getLogger(this.getClass().getSimpleName()).info("book file upload start");

        if (!file.isEmpty()) {
            if (!new File(downloadPath).exists()) {
                Files.createDirectories(Paths.get(downloadPath));
                Logger.getLogger(this.getClass().getSimpleName()).info("created book file folder in " + downloadPath);
            }

            String fileName = slug + bookFileType + "." + FilenameUtils.getExtension(file.getOriginalFilename());
            Path path = Paths.get(downloadPath, fileName);
            resourceURI = "/book-files/" + fileName;
            file.transferTo(path); //uploading user file here
            Logger.getLogger(this.getClass().getSimpleName()).info(fileName + "book file uploaded OK!");
        }

        return resourceURI;
    }

    public Path getBookFilePath(String hash) {
        BookFile bookFile = bookFileRepository.findBookFileByHash(hash);
        return Paths.get(bookFile.getPath());
    }

    public MediaType getBookFileMime(String hash) {
        BookFile bookFile = bookFileRepository.findBookFileByHash(hash);
        String mimeType =
                URLConnection.guessContentTypeFromName(Paths.get(bookFile.getPath()).getFileName().toString());
        if (mimeType != null) {
            return MediaType.parseMediaType(mimeType);
        } else {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

    public byte[] getBookFileByteArray(String hash) throws IOException {
        BookFile bookFile = bookFileRepository.findBookFileByHash(hash);
        Path path = Paths.get(downloadPath, bookFile.getPath());
        return Files.readAllBytes(path);
    }

    public Boolean deleteBookImageFromStorage(String pathOfImageToDelete) {
        String path = uploadPath + "/" + pathOfImageToDelete.substring(pathOfImageToDelete.lastIndexOf('/'));
        File imageToDelete = new File(path);
        if (imageToDelete.exists()){
        return imageToDelete.delete();
        }
        return false;
    }

    public Boolean deleteBookFileFromStorageAndDB(String slug, BookFileType fileType) {
        String pathToDeleteFile = downloadPath + "/" + slug + fileType + "." + fileType.toString().toLowerCase(Locale.ROOT);
        String pathToSearchInDB = "/book-files/" + slug + fileType + "." + fileType.toString().toLowerCase(Locale.ROOT);
        if (bookFileRepository.findBookFileByPath(pathToSearchInDB) != null) {
            bookFileRepository.delete(bookFileRepository.findBookFileByPath(pathToSearchInDB));
        }
        File file = new File(pathToDeleteFile);
        if (file.exists()) {
           return file.delete();
        }
        return false;
    }

    public String saveNewAuthorImage(MultipartFile authorImage, String slug) throws IOException {
        String resourceURI = null;
        Logger.getLogger(this.getClass().getSimpleName()).info("image upload start");

        if (!authorImage.isEmpty()) {
            if (!new File(authorsPhotoPath).exists()) {
                Files.createDirectories(Paths.get(authorsPhotoPath));
                Logger.getLogger(this.getClass().getSimpleName()).info("created image folder in " + authorsPhotoPath);
            }

            String fileName = slug + "." + FilenameUtils.getExtension(authorImage.getOriginalFilename());
            Path path = Paths.get(authorsPhotoPath, fileName);
            resourceURI = "/authors-photos/" + fileName;
            authorImage.transferTo(path); //uploading user file here
            Logger.getLogger(this.getClass().getSimpleName()).info(fileName + "author image uploaded OK!");
        }

        return resourceURI;
    }

    public Boolean deleteAuthorImageFromStorage(String photo) {
        String path = authorsPhotoPath + "/" + photo.substring(photo.lastIndexOf('/'));
        File imageToDelete = new File(path);
        if (imageToDelete.exists()) {
         return imageToDelete.delete();
        }
        return false;
    }
}
