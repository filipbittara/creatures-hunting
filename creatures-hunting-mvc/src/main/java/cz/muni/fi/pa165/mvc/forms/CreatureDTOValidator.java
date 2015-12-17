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
        if (creatureDTO.getHeight() < 0)
            errors.rejectValue("height", "Creature too small");
    } 
}
