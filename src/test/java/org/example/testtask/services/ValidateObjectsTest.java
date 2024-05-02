package org.example.testtask.services;

import org.example.testtask.models.Animal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class ValidateObjectsTest {

    private ValidateObjects validateObjects;

    @Before
    public void setUp() {
        validateObjects = new ValidateObjects();
    }

    @Test
    public void testIsValid_ValidAnimal() {
        // Arrange
        Animal validAnimal = new Animal();
        validAnimal.setName("Dog");
        validAnimal.setType("Mammal");
        validAnimal.setSex("Male");
        validAnimal.setWeight(20);
        validAnimal.setCost(50);

        boolean isValid = validateObjects.isValid(validAnimal);

        Assert.assertTrue(isValid);
    }

    @Test
    public void testIsValid_InvalidName() {

        Animal invalidAnimal = new Animal();
        invalidAnimal.setType("Mammal");
        invalidAnimal.setSex("Male");
        invalidAnimal.setWeight(20);
        invalidAnimal.setCost(50);

        boolean isValid = validateObjects.isValid(invalidAnimal);

        Assert.assertFalse(isValid);
    }

    @Test
    public void testIsValid_InvalidType() {

        Animal invalidAnimal = new Animal();
        invalidAnimal.setName("Dog");
        invalidAnimal.setSex("Male");
        invalidAnimal.setWeight(20);
        invalidAnimal.setCost(50);

        boolean isValid = validateObjects.isValid(invalidAnimal);

        Assert.assertFalse(isValid);
    }
}
