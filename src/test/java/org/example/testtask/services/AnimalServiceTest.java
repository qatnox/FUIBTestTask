package org.example.testtask.services;

import org.example.testtask.models.Animal;
import org.example.testtask.services.AnimalService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AnimalServiceTest {

    private AnimalService animalService;
    private List<Animal> animals;

    @Before
    public void setUp() {
        animalService = new AnimalService();
        animals = new ArrayList<>();
        Animal animal1 = new Animal();
        animal1.setName("Dog");
        animal1.setType("Mammal");
        animal1.setSex("Male");
        animal1.setWeight(20);
        animal1.setCost(50);
        animal1.setCategory("1st");
        animals.add(animal1);

        Animal animal2 = new Animal();
        animal2.setName("Cat");
        animal2.setType("Mammal");
        animal2.setSex("Female");
        animal2.setWeight(15);
        animal2.setCost(30);
        animal2.setCategory("2nd");
        animals.add(animal2);
    }

    @Test
    public void testFilterAnimals_Type() {
        List<Animal> filteredAnimals = animalService.filterAnimals(animals, "Mammal", null, null);

        Assert.assertEquals(2, filteredAnimals.size());
    }

    @Test
    public void testFilterAnimals_Sex() {
        List<Animal> filteredAnimals = animalService.filterAnimals(animals, null, "Male", null);

        Assert.assertEquals(1, filteredAnimals.size());
    }

    @Test
    public void testFilterAnimals_Category() {
        List<Animal> filteredAnimals = animalService.filterAnimals(animals, null, null, "2nd");

        Assert.assertEquals(1, filteredAnimals.size());
    }

    @Test
    public void testSortAnimals_Name() {
        List<Animal> sortedAnimals = animalService.sortAnimals(animals, "name");

        Assert.assertEquals("Cat", sortedAnimals.get(0).getName());
        Assert.assertEquals("Dog", sortedAnimals.get(1).getName());
    }

    @Test
    public void testSortAnimals_Weight() {
        List<Animal> sortedAnimals = animalService.sortAnimals(animals, "weight");

        Assert.assertEquals("Cat", sortedAnimals.get(0).getName());
        Assert.assertEquals("Dog", sortedAnimals.get(1).getName());
    }

    @Test
    public void testSortAnimals_Default() {
        List<Animal> sortedAnimals = animalService.sortAnimals(animals, "unknown");

        Assert.assertEquals("Dog", sortedAnimals.get(0).getName());
        Assert.assertEquals("Cat", sortedAnimals.get(1).getName());
    }
}
