package cz.muni.fi.pa165.facade;
import cz.muni.fi.pa165.dto.CreatureDTO;
import java.util.List;

/**
 * @author Filip Bittara
 */
public interface CreatureFacade {
    /**
     * Creates new creature. Returns id of created creature.
     * @param creature creature to be created
     * @return id of created creature
     */
    public Long createCreature(CreatureDTO creature);
    
    /**
     * Updates existing creature with new data. Area must exist in order to be updated.
     * @param creature creature to be updated
     */
    public void updateCreature(CreatureDTO creature);
    
    /**
     * Removes existing creature. Area must exist in order to be deleted.
     * @param creatureId id of creature to be removed 
     */
    public void deleteCreature(Long creatureId);
    
    /**
     * Retrieves all creatures.
     * @return all creatures in system
     */
    public List<CreatureDTO> getAllCreatures();
    
    /**
     * Returns all creatures in areas in given circle.
     * @param latitude y-coordinate of circle centre
     * @param longitude x-coordinate of circle centre
     * @param radius radius of the circle
     * @return creatures in given circle
     */
    public List<CreatureDTO> getCreaturesInCircle(double latitude, double longitude, double radius);
    
    /**
     * Retrieves all creatures in area with given name.
     * @param areaName name of area to search creatures in
     * @return all creatures in area with given name
     */
    public List<CreatureDTO> getCreaturesByArea(String areaName);
    
    /**
     * Retrieves all creatures that are vulnerable against weapon with given name.
     * @param weaponName name of weapon
     * @return creatures that could be harmed with weapon with given name
     */
    public List<CreatureDTO> getCreaturesByWeapon(String weaponName);
   
    /**
     * Retrieves single creature based on id given. If creature doesn't exist, returns null.
     * @param id id of creature
     * @return creature with given id, null if no creature with given id exists
     */
    public CreatureDTO getCreature(Long id);
}
