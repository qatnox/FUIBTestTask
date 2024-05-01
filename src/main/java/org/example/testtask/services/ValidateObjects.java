package org.example.testtask.services;

import org.example.testtask.models.Animal;
import org.springframework.stereotype.Component;

@Component
public class ValidateObjects {


    public boolean isValid(Animal animal) {
        if (animal.getName() == null || animal.getName().isEmpty()) {
            return false;
        }
        if (animal.getType() == null || animal.getType().isEmpty()) {
            return false;
        }
        if (animal.getSex() == null || animal.getSex().isEmpty()) {
            return false;
        }
        if (animal.getWeight() <= 0) {
            return false;
        }
        return animal.getCost() > 0;
    }
}
