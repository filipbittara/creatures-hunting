/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.mvc.forms;

import cz.muni.fi.pa165.dto.WeaponDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author Ondrej Klein
 */
public class WeaponCreateDTOValidator implements Validator {

    public boolean supports(Class<?> clazz) {
        return WeaponDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        WeaponDTO creatureDTO = (WeaponDTO) target;
        if (creatureDTO.getName() != null) return;
        if (creatureDTO.getGunReach() < 0)
            errors.rejectValue("gunReach", "Gun reach is too small");
    }
}
