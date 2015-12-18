package cz.muni.fi.pa165.rest.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author David Kizivat
 */

@JsonIgnoreProperties({"imageMimeType", "image"})
public class CreatureDTOMixin {

}
