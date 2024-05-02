package org.example.testtask.services;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.example.testtask.models.Animal;
import org.example.testtask.models.Animals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Component
public class AnimalMapper {

    @Value("${readingFile.uploadDir}")
    private String uploadDir;

    @Value("${savedFileDir}")
    private String savedFileDir;
    private final ValidateObjects validateObjects = new ValidateObjects();

    private final Animals animals;

    @Autowired
    public AnimalMapper(Animals animals) {
        this.animals = animals;
    }

    public String getUploadDir() {
        return uploadDir;
    }

    public String getSavedFileDir() {
        return savedFileDir;
    }

    public void extensionCheck(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (FilenameUtils.getExtension(fileName).equals("csv")) {
            mappingCSV(file);
        } else if (FilenameUtils.getExtension(fileName).equals("xml")) {
            mappingXML(file);
        }
    }

    public void mappingCSV(MultipartFile file) {
        String filePath = getUploadDir() + file.getOriginalFilename();

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
                BeanUtils.setProperty(animal, "cost", Integer.parseInt(fields[4]));

                if (validateObjects.isValid(animal)) {
                    animals.addAnimal(animal);
                    animal.setCategory(animal.getCategory());
                }

            }
            saveToFile(animals.getAnimalList());
        } catch (IOException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void mappingXML(MultipartFile file) {
        String filePath = getUploadDir() + file.getOriginalFilename();

        try {
            JAXBContext context = JAXBContext.newInstance(Animals.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Animals root = (Animals) unmarshaller.unmarshal(new File(filePath));

            if (root != null) {
                if (root.getAnimalList() != null && !root.getAnimalList().isEmpty()) {
                    for (Animal animal : root.getAnimalList()) {
                        if (validateObjects.isValid(animal)) {
                            animals.addAnimal(animal);
                            animal.setCategory(animal.getCategory());
                        }
                    }
                }
                saveToFile(animals.getAnimalList());
            }
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveToFile(List<Animal> animals) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(savedFileDir))) {
            writer.write("name" + ";"
                    + "type" + ";"
                    + "sex" + ";"
                    + "weight" + ";"
                    + "cost" + ";"
                    + "category" + "\n");
            for (Animal animal : animals) {
                writer.write(animal.getName() + ";"
                        + animal.getType() + ";"
                        + animal.getSex() + ";"
                        + animal.getWeight() + ";"
                        + animal.getCost() + ";"
                        + animal.getCategory() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to save animals to file", e);
        }
    }
}
