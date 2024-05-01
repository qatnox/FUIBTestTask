package org.example.testtask.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.testtask.models.Animal;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class AnimalService {

    List<Animal> animalsFromFile = new ArrayList<>();
    public void readFile(File file) {
        try (FileReader reader = new FileReader(file);
             BufferedReader buffReader = new BufferedReader(reader)) {
            String line;
            while ((line = buffReader.readLine()) != null) {
                Animal animal = new Animal();
                animalsFromFile.add(animal);
            }
        } catch (IOException e) {
            System.err.println("The process cannot access the file because it is being used by another process.");
            System.exit(0);
        }
    }

    public List<Animal> getAnimalsFromFile() {
        return animalsFromFile;
    }

    public List<Animal> filterAnimals(List<Animal> animals, String name,
                                      String type, String sex, int weight,
                                      int cost, String category) {

        System.out.println("===== filterAnimals() method - #1 =====");

        Stream<Animal> filteredStream = animals.stream();

        if (name != null) {
            filteredStream = filteredStream.filter(animal -> animal.getName().equalsIgnoreCase(type));
        }
        if (type != null) {
            filteredStream = filteredStream.filter(animal -> animal.getType().equalsIgnoreCase(type));
        }
        if (sex != null) {
            filteredStream = filteredStream.filter(animal -> animal.getSex().equalsIgnoreCase(type));
        }
        if (weight >= 0) {
            filteredStream = filteredStream.filter(animal -> animal.getWeight() == weight);
        }
        if (cost >= 0) {
            filteredStream = filteredStream.filter(animal -> animal.getCost() == cost);
        }
        if (category != null) {
            filteredStream = filteredStream.filter(animal -> animal.getCategory().equalsIgnoreCase(type));
        }

        return filteredStream.collect(Collectors.toList());
    }

    public List<Animal> sortAnimals(List<Animal> animals, String sortBy) {

        System.out.println("===== sortAnimals() method - #1 =====");

        if (sortBy != null && sortBy.equalsIgnoreCase("name")) {
            return animals.stream()
                    .sorted(Comparator.comparing(Animal::getName))
                    .collect(Collectors.toList());
        }

        return animals;
    }
}
