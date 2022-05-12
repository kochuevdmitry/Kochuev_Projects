package org.example.app.services;

import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileDownloadService {

    private final Logger logger = Logger.getLogger(FileDownloadService.class);

    private final String baseFolder = System.getProperty("catalina.home");

    public List<Path> getListFiles() {

        logger.info("baseFolder - " + baseFolder);

        List<Path> fileList = new ArrayList<>();

        try {
            Path pathBase = Paths.get(baseFolder);

            Files.walk(pathBase.resolve("external_uploads"))
                    .filter(Files::isRegularFile)
                    .forEach(f -> fileList.add(f));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileList;
    }

    public Resource loadFile(String filename) {
        try {
            Path rootLocation = Paths.get(baseFolder).resolve("external_uploads");
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("FAIL!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error! -> message = " + e.getMessage());
        }
    }
}
