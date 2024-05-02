package org.example.testtask.services;

import org.example.testtask.models.Animal;
import org.example.testtask.models.Animals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class AnimalMapperTest {

    @Mock
    private MultipartFile multipartFile;

    @Mock
    private Animals animals;

    private AnimalMapper animalMapper;

    @Before
    public void setUp() {
        animalMapper = new AnimalMapper(animals);
    }

    @Test
    public void testExtensionCheck_CSV() throws IOException {
        Mockito.when(multipartFile.getOriginalFilename()).thenReturn("test.csv");

        animalMapper.extensionCheck(multipartFile);

        Mockito.verify(animalMapper, Mockito.times(1)).mappingCSV(multipartFile);
    }

    @Test
    public void testExtensionCheck_XML() throws IOException {

        Mockito.when(multipartFile.getOriginalFilename()).thenReturn("test.xml");

        animalMapper.extensionCheck(multipartFile);

        Mockito.verify(animalMapper, Mockito.times(1)).mappingXML(multipartFile);
    }

    @Test
    public void testMappingCSV() throws IOException {
        String[] fields = {"Dog", "Mammal", "Male", "20", "50"};
        Mockito.when(multipartFile.getOriginalFilename()).thenReturn("test.csv");
        Mockito.when(multipartFile.getInputStream()).thenReturn(getClass().getClassLoader().getResourceAsStream("test.csv"));

        animalMapper.mappingCSV(multipartFile);

        Mockito.verify(animals, Mockito.times(1)).addAnimal(ArgumentMatchers.any(Animal.class));
    }

    @Test
    public void testMappingXML() throws IOException {

        Mockito.when(multipartFile.getOriginalFilename()).thenReturn("test.xml");
        Mockito.when(multipartFile.getInputStream()).thenReturn(getClass().getClassLoader().getResourceAsStream("test.xml"));

        animalMapper.mappingXML(multipartFile);

        Mockito.verify(animals, Mockito.times(1)).addAnimal(ArgumentMatchers.any(Animal.class));
    }

    @Test
    public void testSaveToFile() throws IOException {

        List<Animal> animalList = new ArrayList<>();
        Animal animal = new Animal();
        animal.setName("Dog");
        animal.setType("Mammal");
        animal.setSex("Male");
        animal.setWeight(20);
        animal.setCost(50);
        animal.setCategory("1st");
        animalList.add(animal);

        animalMapper.saveToFile(animalList);

        File file = new File(animalMapper.getSavedFileDir());
        Assert.assertTrue(file.exists());

        file.delete();
    }
}
