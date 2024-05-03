package org.example.testtask.models;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Component;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@Component
@XmlRootElement(name = "animals")
public class Animals {

    @XmlElement(name = "animal")
    public List<Animal> animals;

    @ApiOperation("Getting animal list")
    public List<Animal> getAnimalList() {
        return animals;

    }
    public Animals() {
        this.animals = new ArrayList<>();
    }

    public void addAnimal(Animal animal) {
        this.animals.add(animal);
    }

    public void setAnimalList(List<Animal> animals) {
        this.animals = animals;
    }
}
