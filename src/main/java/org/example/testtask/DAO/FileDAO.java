package org.example.testtask.DAO;

import org.example.testtask.services.AnimalMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Component
public class FileDAO {

    private final String uploadDir = "C:\\uploads";
    private final AnimalMapper animalMapper;

    public FileDAO(AnimalMapper animalMapper) {
        this.animalMapper = animalMapper;
    }

    public void save(MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);

            Files.createDirectories(filePath.getParent());
            file.transferTo(filePath);

            animalMapper.extensionCheck(file);
        }

    }
}
