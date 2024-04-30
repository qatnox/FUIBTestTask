package org.example.testtask.controllers;

import org.example.testtask.DAO.FileDAO;
import org.example.testtask.models.File;

import org.example.testtask.services.AnimalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/files")
public class FilesController {

    private final FileDAO fileDAO;

    @Autowired
    public FilesController(FileDAO fileDAO) {
        this.fileDAO = fileDAO;
    }

    @GetMapping("/uploads")
    public String page(@ModelAttribute("file") File file) {
        System.out.println("test");
        return "files/uploads";
    }

    //TODO: Show information ?error=...

    @PostMapping("/uploads")
    public String upload(@RequestParam("file") MultipartFile file) {
        System.out.println("[UPLOADING]");

        String fileName = file.getOriginalFilename();
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);

        if (!fileExtension.equalsIgnoreCase("csv") && !fileExtension.equalsIgnoreCase("xml")) {
            return "redirect:/files/uploads?error=incorrectExtension";
        }

        try {
            fileDAO.save(file);
        } catch (IOException e) {
            return "redirect:/files/uploads?error=unableToUpload";
        }

        return "redirect:/files/result";
    }
}
