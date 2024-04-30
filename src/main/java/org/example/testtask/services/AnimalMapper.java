package org.example.testtask.services;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.example.testtask.models.Animal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AnimalMapper {

    @Value("${readingFile.uploadDir}")
    private String uploadDir;
    private final ValidateObjects validateObjects = new ValidateObjects();

    public String getUploadDir() {
        return uploadDir;
    }

    public void extensionCheck(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (FilenameUtils.getExtension(fileName).equals("csv")) {
            mappingCSV(file);
        } else if (FilenameUtils.getExtension(fileName).equals("xml")) {
            mappingXML(file);
        }
    }

    //TODO: Remove sout(animal)

    public void mappingCSV(MultipartFile file) {
        String filePath = getUploadDir() + file.getOriginalFilename();
        List<Animal> animals = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {

                String[] fields = line.split(",");
                Animal animal = new Animal();

                BeanUtils.setProperty(animal, "name", fields[0]);
                BeanUtils.setProperty(animal, "type", fields[1]);
                BeanUtils.setProperty(animal, "sex", fields[2]);
                BeanUtils.setProperty(animal, "weight", Integer.parseInt(fields[3]));
                BeanUtils.setProperty(animal, "cost", Integer.parseInt(fields[3]));

                if (validateObjects.isValid(animal)) {
                    animals.add(animal);
                    System.out.println(animal); // TEMPORARY ==================================================================
                }
            }

        } catch (IOException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    //TODO: finish the method mappingXML

    public void mappingXML(MultipartFile file) {
        String filePath = getUploadDir() + file.getOriginalFilename();
        Animal animal;

        try {
            JAXBContext context = JAXBContext.newInstance(Animal.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            animal = (Animal) unmarshaller.unmarshal(new File(filePath));

            /*if(validateObjects.isValid(animal)) {
                animals.add(animal);
                System.out.println(animal);
            }*/

        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
