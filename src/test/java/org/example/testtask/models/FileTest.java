package org.example.testtask.models;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class FileTest {

    @Test
    public void testGetFile() {
        MultipartFile multipartFile = new MockMultipartFile("test.txt", new byte[]{});

        File file = new File(multipartFile);

        Assert.assertNotNull(file.getFile());
        Assert.assertEquals(multipartFile, file.getFile());
    }

    @Test
    public void testSetFile() {
        MultipartFile multipartFile1 = new MockMultipartFile("test1.txt", new byte[]{});
        MultipartFile multipartFile2 = new MockMultipartFile("test2.txt", new byte[]{});
        File file = new File(multipartFile1);

        file.setFile(multipartFile2);

        Assert.assertEquals(multipartFile2, file.getFile());
    }
}
