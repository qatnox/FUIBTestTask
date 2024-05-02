package org.example.testtask.DAO;

import org.example.testtask.services.AnimalMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

@RunWith(MockitoJUnitRunner.class)
public class FileDAOTest {

    @Mock
    private AnimalMapper animalMapper;

    @InjectMocks
    private FileDAO fileDAO;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSave() throws IOException {

        MultipartFile mockFile = Mockito.mock(MultipartFile.class);
        String fileName = "testfile.txt";
        Mockito.when(mockFile.isEmpty()).thenReturn(false);
        Mockito.when(mockFile.getOriginalFilename()).thenReturn(fileName);

        fileDAO.save(mockFile);

        Mockito.verify(mockFile, Mockito.times(1)).isEmpty();
        Mockito.verify(mockFile, Mockito.times(1)).getOriginalFilename();
        Mockito.verify(mockFile, Mockito.times(1)).transferTo(ArgumentMatchers.any(Path.class));
        Mockito.verify(animalMapper, Mockito.times(1)).extensionCheck(mockFile);
    }

    @Test(expected = IOException.class)
    public void testSave_IOException() throws IOException {
        MultipartFile mockFile = Mockito.mock(MultipartFile.class);
        Mockito.when(mockFile.isEmpty()).thenReturn(false);
        Mockito.when(mockFile.getOriginalFilename()).thenReturn("testfile.txt");
        Mockito.doThrow(new IOException()).when(mockFile).transferTo(ArgumentMatchers.any(Path.class));

        fileDAO.save(mockFile);
    }

    @Test
    public void testSave_EmptyFile() throws IOException {
        MultipartFile mockFile = Mockito.mock(MultipartFile.class);
        Mockito.when(mockFile.isEmpty()).thenReturn(true);

        fileDAO.save(mockFile);

        Mockito.verify(mockFile, Mockito.times(1)).isEmpty();
        Mockito.verifyNoInteractions(mockFile, animalMapper);
    }
}
