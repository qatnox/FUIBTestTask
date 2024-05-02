package org.example.testtask.controllers;

import org.example.testtask.DAO.FileDAO;
import org.example.testtask.models.Animal;
import org.example.testtask.models.Animals;
import org.example.testtask.models.File;
import org.example.testtask.services.AnimalMapper;
import org.example.testtask.services.AnimalService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class FilesControllerTest {

    @Mock
    private FileDAO fileDAO;

    @Mock
    private AnimalService animalService;

    @Mock
    private AnimalMapper animalMapper;

    @Mock
    private Animals animalsList;

    @InjectMocks
    private FilesController filesController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testPage() {
        MultipartFile mockFile = Mockito.mock(MultipartFile.class);
        String result = filesController.page(new File(mockFile));
        Assert.assertEquals("files/uploads", result);
    }



    @Test
    public void testUpload_Success() throws IOException {
        MultipartFile mockFile = Mockito.mock(MultipartFile.class);

        String result = filesController.upload(mockFile);

        Mockito.verify(fileDAO, Mockito.times(1)).save(mockFile);
        Assert.assertEquals("redirect:/files/animals", result);
    }

    @Test
    public void testUpload_InvalidFileType() {
        MultipartFile mockFile = Mockito.mock(MultipartFile.class);
        Mockito.when(mockFile.getOriginalFilename()).thenReturn("testfile.exe");

        String result = filesController.upload(mockFile);

        Mockito.verifyNoInteractions(fileDAO);
        Assert.assertEquals("redirect:/files/error", result);
    }

    @Test
    public void testUpload_IOException() throws IOException {
        MultipartFile mockFile = Mockito.mock(MultipartFile.class);
        Mockito.doThrow(new IOException()).when(fileDAO).save(mockFile);

        String result = filesController.upload(mockFile);

        Mockito.verify(fileDAO, Mockito.times(1)).save(mockFile);
        Assert.assertEquals("redirect:/files/error", result);
    }

    @Test
    public void testErrorPage() {
        String result = filesController.errorPage();

        Assert.assertEquals("files/error", result);
    }

    @Test
    public void testGetAnimals() {
        List<Animal> mockAnimals = new ArrayList<>();
        Mockito.when(animalsList.getAnimalList()).thenReturn(mockAnimals);
    }
}