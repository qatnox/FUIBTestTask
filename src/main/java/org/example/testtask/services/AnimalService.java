package org.example.testtask.services;

import org.example.testtask.models.Animal;
import org.example.testtask.models.File;
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

@Component
public class AnimalService {

    AnimalMapper animalMapper;
    List<Animal> animalsFromFile = new ArrayList<>();
    /*public void readFile() {
        try (FileReader reader = new FileReader(animalMapper.getSavedFileDir());
             BufferedReader buffReader = new BufferedReader(reader)) {
            String line;
            while ((line = buffReader.readLine()) != null) {
                String[] data = line.split(";"); // Предполагается, что данные разделены запятой

                // Перевірка довжини масиву для уникнення виходу за межі масиву
                if (data.length >= 4) {
                    String name = data[0].trim();
                    String type = data[1].trim();
                    String sex = data[2].trim();
                    int weight = Integer.parseInt(data[3].trim()); // Предполагается, что вага - це ціле число

                    // Опціонально, якщо ви маєте більше даних про тварин, що потрібно зчитати
                    // Ви можете продовжити додавати дані до об'єкту Animal

                    Animal animal = new Animal(name, type, sex, weight);
                    animalsFromFile.add(animal);
                }
            }
        } catch (IOException e) {
            System.err.println("The process cannot access the file because it is being used by another process.");
            System.exit(0);
        }
    }*/

    public List<Animal> getAnimalsFromFile() {
        return animalsFromFile;
    }


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
