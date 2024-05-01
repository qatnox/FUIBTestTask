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
    public FilesController(FileDAO fileDAO, AnimalService animalService, AnimalMapper animalMapper) {
        this.fileDAO = fileDAO;
        this.animalService = animalService;
        this.animalMapper = animalMapper;
    }

    @GetMapping("/uploads")
    public String page(@ModelAttribute("file") File file) {
        return "files/uploads";
    }

    //TODO: Show information ?error=...

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
    public ResponseEntity<List<Animal>> getAnimals(Model model,
                             @RequestParam(value = "name", required = false) String name,
                             @RequestParam(value = "type", required = false) String type,
                             @RequestParam(value = "sex", required = false) String sex,
                             @RequestParam(value = "weight", required = false) Integer weight,
                             @RequestParam(value = "cost", required = false) Integer cost,
                             @RequestParam(value = "category", required = false) String category,
                             @RequestParam(value = "sortBy", required = false) String sortBy) {

        List<Animal> animals = animalService.getAnimalsFromFile();


        animals = animalService.filterAnimals(animals, name, type, sex, weight, cost, category);
        animals = animalService.sortAnimals(animals, sortBy);

        model.addAttribute("animals", animals);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(animals);
    }
}
