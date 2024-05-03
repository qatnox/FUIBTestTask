package org.example.testtask.services;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.testtask.models.Animal;

import org.example.testtask.models.Animals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Api(tags = "Animal Service", description = "Service for animal data management")
@Component
public class AnimalService {

    @Autowired
    private Animals animalsList;

    @Value("${savedFileDir}")
    private String savedFileDir;

    List<Animal> animalsFromFile = new ArrayList<>();

    @ApiOperation("Read animals from the file")
    public void readFile() {
        try (FileReader reader = new FileReader(savedFileDir);
             BufferedReader buffReader = new BufferedReader(reader)) {

            String line;
            buffReader.readLine();
            while ((line = buffReader.readLine()) != null) {
                String[] data = line.split(";");

                if (data.length >= 6) {
                    String name = data[0].trim();
                    String type = data[1].trim();
                    String sex = data[2].trim();
                    int weight = Integer.parseInt(data[3].trim());
                    int cost = Integer.parseInt(data[4].trim());
                    String category = data[5].trim();

                    Animal animal = new Animal(name, type, sex, weight, cost, category);
                    animalsFromFile.add(animal);

                }
            }
            animalsList.setAnimalList(animalsFromFile);
        } catch (IOException e) {
            System.err.println("Unable to read");
        }
    }


    public List<Animal> getAnimalsFromFile() {
        return animalsFromFile;
    }

    @ApiOperation("Filter animals by 3 parameters")
    public List<Animal> filterAnimals(List<Animal> animals, String type, String sex, String category) {
        Stream<Animal> filteredStream = animals.stream();

        if (type != null) {
            filteredStream = filteredStream.filter(animal -> animal.getType().equalsIgnoreCase(type));
        }
        if (sex != null) {
            filteredStream = filteredStream.filter(animal -> animal.getSex().equalsIgnoreCase(sex));
        }
        if (category != null) {
            filteredStream = filteredStream.filter(animal -> animal.getCategory().equalsIgnoreCase(category));
        }

        return filteredStream.collect(Collectors.toList());
    }

    @ApiOperation("Sort animals (?sortBy=)")
    public List<Animal> sortAnimals(List<Animal> animals, String sortBy) {
        if (sortBy != null) {
            System.out.println("Sorting by: " + sortBy);
            Comparator<Animal> comparator = null;
            switch (sortBy.toLowerCase()) {
                case "name":
                    comparator = Comparator.comparing(Animal::getName);
                    break;
                case "type":
                    comparator = Comparator.comparing(Animal::getType);
                    break;
                case "sex":
                    comparator = Comparator.comparing(Animal::getSex);
                    break;
                case "weight":
                    comparator = Comparator.comparingInt(Animal::getWeight);
                    break;
                case "cost":
                    comparator = Comparator.comparingInt(Animal::getCost);
                    break;
                case "category":
                    comparator = Comparator.comparing(Animal::getCategory);
                    break;
                default:
                    break;
            }
            if (comparator != null) {
                return animals.stream()
                        .sorted(comparator)
                        .collect(Collectors.toList());
            }
        }
        return animals;
    }
}
