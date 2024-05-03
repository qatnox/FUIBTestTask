package org.example.testtask.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.testtask.DAO.FileDAO;
import org.example.testtask.models.Animal;
import org.example.testtask.models.Animals;
import org.example.testtask.models.File;
import org.example.testtask.services.AnimalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Api(tags = "Animal API", description = "Endpoints for managing animals")
@Controller
@RequestMapping("/files")
public class FilesController {

    private final FileDAO fileDAO;
    private final AnimalService animalService;

    private final Animals animalsList;

    @Autowired
    public FilesController(FileDAO fileDAO, AnimalService animalService, Animals animalsList) {
        this.fileDAO = fileDAO;
        this.animalService = animalService;
        this.animalsList = animalsList;
    }

    @ApiOperation("Render the upload page")
    @GetMapping("/uploads")
    public String page(@ModelAttribute("file") File file) {
        return "files/uploads";
    }

    @ApiOperation("Upload a file (.csv / .xml)")
    @PostMapping("/uploads")
    public String upload(@RequestParam("file") MultipartFile file) {

        String fileName = file.getOriginalFilename();

        if (fileName == null) {
            return "redirect:/files/error";
        }

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

    @ApiOperation("Render the error page")
    @GetMapping("/error")
    public String errorPage() {
        return "files/error";
    }


    @ApiOperation("Get the list of animals")
    @GetMapping("/animals")
    public ResponseEntity<List<Animal>> getAnimals(
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "sex", required = false) String sex,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "sortBy", required = false) String sortBy) {

        List<Animal> animals = animalsList.getAnimalList();

        if (animalsList.getAnimalList().isEmpty()) {
            animalService.readFile();
            animals = animalService.getAnimalsFromFile();
        }

        animals = animalService.filterAnimals(animals, type, sex, category);
        animals = animalService.sortAnimals(animals, sortBy);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(animals);
    }
}
