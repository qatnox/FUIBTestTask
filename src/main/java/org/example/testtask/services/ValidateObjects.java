package org.example.testtask.services;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.testtask.models.Animal;
import org.springframework.stereotype.Component;

@Api(tags = "Object Validator", description = "Validator for animal objects")
@Component
public class ValidateObjects {

    @ApiOperation("Check if the 'animal' is valid")
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
