/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.persistence.entity.Area;
import cz.muni.fi.pa165.persistence.entity.Creature;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Filip Bittara
 */
public interface AreaService {
    /**
     * Creates new area. Returns id of created area.
     * @param area area to be created
     * @return instance of the area created
     */
    Area createArea(Area area);
    /**
     * Updates existing area with new data. Area must exist in order to be updated.
     * @param area area to be updated
     */
    void updateArea(Area area);
    /**
     * Removes existing area. Area must exist in order to be deleted.
     * @param area area to be removed 
     */
    void deleteArea(Area area);
    /**
     * Retrieves all areas.
     * @return all areas in system
     */
    List<Area> getAllAreas();
    /**
     * Returns areas where creature was spotted.
     * @param creature creature for whom areas should be returned
     * @return areas where creature is
     */
    Set<Area> getAreasForCreature(Creature creature);
    /**
     * Retrieves single area based on id given. If area doesn't exist, returns null.
     * @param id id of area
     * @return area with given id, null if no area with given id exists
     */
    Area getArea(Long id);
    /**
     * Adds creature to area.
     * @param creatureId id of creature to be added
     * @param areaId id of area to add creature to
     */
    void addCreatureToArea(Creature creature, Area area);
    /**
     * Removes creature from area.
     * @param creatureId id of creature to be removed
     * @param areaId id of area to remove creature from
     */
    void removeCreatureFromArea(Creature creature, Area area);
}
