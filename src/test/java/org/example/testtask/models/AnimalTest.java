package org.example.testtask.models;

import org.junit.Assert;
import org.junit.Test;

public class AnimalTest {

    @Test
    public void testGetCategory_FirstCategory() {
        Animal animal = new Animal();
        animal.setCost(15);

        String category = animal.getCategory();

        Assert.assertEquals("1st", category);
    }

    @Test
    public void testGetCategory_SecondCategory() {
        Animal animal = new Animal();
        animal.setCost(35);

        String category = animal.getCategory();

        Assert.assertEquals("2nd", category);
    }

    @Test
    public void testGetCategory_ThirdCategory() {
        Animal animal = new Animal();
        animal.setCost(55);

        String category = animal.getCategory();

        Assert.assertEquals("3rd", category);
    }

    @Test
    public void testGetCategory_FourthCategory() {
        Animal animal = new Animal();
        animal.setCost(75);

        String category = animal.getCategory();

        Assert.assertEquals("4th", category);
    }

    @Test
    public void testToString() {
        Animal animal = new Animal();
        animal.setName("Dog");
        animal.setType("Mammal");
        animal.setSex("Male");
        animal.setWeight(20);
        animal.setCost(30);
        animal.setCategory("2nd");

        String result = animal.toString();

        Assert.assertEquals("Animal{name='Michael', type='cat', sex='male', weight=20, cost=30, category='2nd'}", result);
    }
}
