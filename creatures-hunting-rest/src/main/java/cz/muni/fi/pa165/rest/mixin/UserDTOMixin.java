package cz.muni.fi.pa165.rest.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author David Kizivat
 */

@JsonIgnoreProperties({"password", "image"})
public class UserDTOMixin {

}
