package cz.muni.fi.pa165.mvc.forms;

import cz.muni.fi.pa165.dto.AreaDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Kizivat
 */
public class AreaCreateDTOValidator implements Validator {

    public boolean supports(Class<?> clazz) {
        return AreaDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AreaDTO creatureDTO = (AreaDTO) target;
        if (creatureDTO.getName() != null) return;
        if (creatureDTO.getLatitude() < -180 || creatureDTO.getLatitude() > 180)
            errors.rejectValue("latitude", "Latitude out of range");
        if (creatureDTO.getLongitude() < -180 || creatureDTO.getLongitude() > 180)
            errors.rejectValue("longitude", "longitude out of range");
    }
}