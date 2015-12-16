package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.AreaDTO;
import cz.muni.fi.pa165.persistence.entity.Creature;

import java.util.List;

/**
 * @author Filip Bittara
 */
public interface AreaFacade {
    /**
     * Creates new area. Returns id of created area.
     * @param area area to be created
     * @return id of created area
     */
    public Long createArea(AreaDTO area);
    
    /**
     * Updates existing area with new data. Area must exist in order to be updated.
     * @param area area to be updated
     */
    public void updateArea(AreaDTO area);
    
    /**
     * Removes existing area. Area must exist in order to be deleted.
     * @param area area to be removed 
     */
    public void deleteArea(AreaDTO area);
    
    /**
     * Retrieves all areas.
     * @return all areas in system
     */
    public List<AreaDTO> getAllAreas();
    
    /**
     * Returns areas where creature was spotted.
     * @param creature creature for whom areas should be returned
     * @return areas where creature is
     */
    public AreaDTO getAreaForCreature(Creature creature);
    
    /**
     * Retrieves single area based on id given. If area doesn't exist, returns null.
     * @param id id of area
     * @return area with given id, null if no area with given id exists
     */
    public AreaDTO getArea(Long id);
    
    /**
     * Adds creature to area.
     * @param creatureId id of creature to be added
     * @param areaId id of area to add creature to
     */
    public void addCreatureToArea(Long creatureId, Long areaId);
    
    /**
     * Removes creature from area.
     * @param creatureId id of creature to be removed
     * @param areaId id of area to remove creature from
     */
    public void removeCreatureFromArea(Long creatureId, Long areaId);
}
