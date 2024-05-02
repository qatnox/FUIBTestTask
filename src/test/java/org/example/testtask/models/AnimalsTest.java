package org.example.testtask.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class AnimalsTest {

    private Animals animals;

    @Before
    public void setUp() {
        animals = new Animals();
    }

    @Test
    public void testGetAnimalList_EmptyList() {

        List<Animal> animalList = animals.getAnimalList();

        Assert.assertTrue(animalList.isEmpty());
    }

    @Test
    public void testAddAnimal() {

        Animal animal = new Animal();

        animals.addAnimal(animal);
        List<Animal> animalList = animals.getAnimalList();

        Assert.assertEquals(1, animalList.size());
        Assert.assertEquals(animal, animalList.get(0));
    }

    @Test
    public void testSetAnimalList() {
        Animal animal1 = new Animal();
        Animal animal2 = new Animal();
        List<Animal> animalList = new ArrayList<>();
        animalList.add(animal1);
        animalList.add(animal2);

        animals.setAnimalList(animalList);
        List<Animal> animalListResult = animals.getAnimalList();

        Assert.assertEquals(2, animalListResult.size());
        Assert.assertEquals(animal1, animalListResult.get(0));
        Assert.assertEquals(animal2, animalListResult.get(1));
    }
}
