/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.mvc.forms;

import cz.muni.fi.pa165.dto.CreatureDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author Filip Bittara
 */
public class CreatureDTOValidator implements Validator {

    public boolean supports(Class<?> clazz) {
        return CreatureDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CreatureDTO creatureDTO = (CreatureDTO) target;
        if (creatureDTO.getName() != null) return;
        if (creatureDTO.getHeight() < 0)
            errors.rejectValue("height", "Creature too small");
        if (creatureDTO.getWeight() < 0)
            errors.rejectValue("weight", "Creature too light");
        if (creatureDTO.getAgility() < 0 || creatureDTO.getAgility() > 10)
            errors.rejectValue("agility", "Creature's agility out of range");
        if (creatureDTO.getFerocity() < 0 || creatureDTO.getFerocity() > 10)
            errors.rejectValue("Ferocity", "Creature's ferocity out of range");
    } 
}
