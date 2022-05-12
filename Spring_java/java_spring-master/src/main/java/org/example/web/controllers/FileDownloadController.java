package org.example.web.controllers;


import org.apache.log4j.Logger;
import org.example.app.services.FileDownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/books")
public class FileDownloadController {

    private Logger logger = Logger.getLogger(BookShelfController.class);
    private FileDownloadService fileDownloadService;

    @Autowired
    public FileDownloadController(FileDownloadService fileDownloadService) {
        this.fileDownloadService = fileDownloadService;
    }

    @GetMapping("/download")
    public String download(Model model) {
        logger.info("open downloads");
        model.addAttribute("fileList", fileDownloadService.getListFiles());

        return "download";
    }

    @GetMapping("/download/")
    public ResponseEntity downloadFile(@RequestParam("filename") String filename) {
        logger.info("download file " + filename);
        Resource file = fileDownloadService.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

}
