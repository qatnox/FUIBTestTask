package org.example.testtask.controllers;

import org.example.testtask.DAO.FileDAO;
import org.example.testtask.models.Animal;
import org.example.testtask.models.Animals;
import org.example.testtask.models.File;

import org.example.testtask.services.AnimalMapper;
import org.example.testtask.services.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/files")
public class FilesController {

    private final FileDAO fileDAO;
    private final AnimalService animalService;
    private final AnimalMapper animalMapper;

    @Autowired
    private final Animals animalsList;


    @Autowired
    public FilesController(FileDAO fileDAO, AnimalService animalService, AnimalMapper animalMapper, Animals animalsList) {
        this.fileDAO = fileDAO;
        this.animalService = animalService;
        this.animalMapper = animalMapper;
        this.animalsList = animalsList;
    }

    @GetMapping("/uploads")
    public String page(@ModelAttribute("file") File file) {
        return "files/uploads";
    }


    @PostMapping("/uploads")
    public String upload(@RequestParam("file") MultipartFile file) {

        String fileName = file.getOriginalFilename();
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);

        if (!fileExtension.equalsIgnoreCase("csv") &&
                !fileExtension.equalsIgnoreCase("xml")) {
            return "redirect:/files/error";
        }

        try {
            fileDAO.save(file);
        } catch (IOException e) {
            return "redirect:/files/error";
        }

        return "redirect:/files/animals";
    }

    @GetMapping("/error")
    public String errorPage() {
        return "files/error";
    }

    @GetMapping("/animals")
    public ResponseEntity<List<Animal>> getAnimals(
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "sex", required = false) String sex,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "sortBy", required = false) String sortBy) {

        List<Animal> animals = animalsList.getAnimalList();

        /*if (animals.isEmpty()) {
            animalService.readFile();
            animals = animalService.getAnimalsFromFile();
            System.out.println(animals);
        }*/

        animals = animalService.filterAnimals(animals, type, sex, category);
        animals = animalService.sortAnimals(animals, sortBy);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(animals);
    }
}
