package cz.muni.fi.pa165.rest.controllers;

import cz.muni.fi.pa165.dto.CreatureDTO;
import cz.muni.fi.pa165.facade.CreatureFacade;
import cz.muni.fi.pa165.rest.exceptions.ResourceAlreadyExistingException;
import cz.muni.fi.pa165.rest.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.rest.exceptions.ResourceNotModifiedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author David Kizivat
 */
@RestController
@RequestMapping("/creatures")
public class CreatureController {

    final static Logger log = LoggerFactory.getLogger(CreatureController.class);

    @Autowired
    private CreatureFacade creatureFacade;

    /**
     * Get list of all Creatures
     * curl -i -X GET
     * http://localhost:8080/pa165/rest/creatures/1
     *
     * @return List<CreatureDTO>
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<CreatureDTO> getCreatures() {
        log.debug("REST getting all creatures");
        return creatureFacade.getAllCreatures();
    }

    /**
     * Get a creature with id
     * curl -i -X GET
     * http://localhost:8080/pa165/rest/creatures/1
     *
     * @param id creature id
     * @return CreatureDTO
     * @throws ResourceNotFoundException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final CreatureDTO getCreature(@PathVariable("id") long id) throws Exception {

        log.debug("REST getting product with ID {}", id);

        try {
            CreatureDTO creatureDTO = creatureFacade.getCreature(id);
            return creatureDTO;
        } catch (Exception ex) {
            throw new ResourceNotFoundException();
        }
    }

    /**
     * Creates new creature by POST method
     * curl -i -X POST
     * http://localhost:8080/pa165/rest/creatures/1
     *
     * @param creature CreatureDTO with creature's attributes
     * @return CreatureDTO created creature
     * @throws ResourceAlreadyExistingException
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final CreatureDTO createCreature(@RequestBody CreatureDTO creature) throws Exception {

        log.debug("REST creating creature named {}", creature.getName());

        try {
            Long id = creatureFacade.createCreature(creature);
            return creatureFacade.getCreature(id);
        } catch (Exception ex) {
            throw new ResourceAlreadyExistingException();
        }
    }

    /**
     * Updates creature by PUT method
     * curl -i -X PUT -H "Content-Type: application/json"
     * --data '{"name":"new_title"}'
     * http://localhost:8080/pa165/rest/creatures/1
     *
     * @param id creature id
     * @param editedCreature CreatureDTO with edited creature's attributes
     * @throws ResourceNotFoundException
     * @throws ResourceNotModifiedException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final CreatureDTO updateCreature(@PathVariable("id") long id, @RequestBody CreatureDTO editedCreature) throws Exception {

        log.debug("REST editing creature with id {}", id);

        try {
            creatureFacade.getCreature(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException();
        }

        try{
            editedCreature.setId(id);
            creatureFacade.updateCreature(editedCreature);
        }catch(Exception ex){
            throw new ResourceNotModifiedException();
        }

        return editedCreature;
    }

    /**
     * Get a creatures in area
     * curl -i -X GET
     * http://localhost:8080/pa165/rest/area/Name/creature
     *
     * @param name area name
     * @return list of CreatureDTO
     * @throws ResourceNotFoundException
     */
    @RequestMapping(value = "/area/{name}/creature", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<CreatureDTO> getCreaturesByArea(@PathVariable("name") String name) throws Exception {

        log.debug("REST getting creatures from area named {}", name);

        try {
            List<CreatureDTO> creatures = creatureFacade.getCreaturesByArea(name);
            return creatures;
        } catch (Exception ex) {
            throw new ResourceNotFoundException();
        }
    }

}
