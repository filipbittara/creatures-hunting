package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.persistence.entity.Creature;
import java.util.List;
import java.util.Set;

/**
 * @author Filip Bittara
 */
public interface CreatureService {
    /**
     * Creates new creature. Returns id of created creature.
     * @param creature creature to be created
     * @return instance of the creature created
     */
    Creature createCreature(Creature creature);
    /**
     * Updates existing creature with new data. Area must exist in order to be updated.
     * @param creature creature to be updated
     */
    void updateCreature(Creature creature);
    /**
     * Removes existing creature. Area must exist in order to be deleted.
     * @param creatureId id of creature to be removed 
     */
    void deleteCreature(Long creatureId);
    /**
     * Retrieves all creatures.
     * @return all creatures in system
     */
    List<Creature> getAllCreatures();
    /**
     * Returns all creatures in areas in given circle.
     * @param latitude y-coordinate of circle centre
     * @param longitude x-coordinate of circle centre
     * @param radius radius of the circle
     * @return creatures in given circle
     */
    Set<Creature> getCreaturesInCircle(double latitude, double longitude, double radius);
    /**
     * Retrieves all creatures in area with given name.
     * @param areaName name of area to search creatures in
     * @return all creatures in area with given name
     */
    Set<Creature> getCreaturesByArea(String areaName);
    /**
     * Retrieves all creatures that are vulnerable against weapon with given name.
     * @param weaponName name of weapon
     * @return creatures that could be harmed with weapon with given name
     */
    Set<Creature> getCreaturesByWeapon(String weaponName);
    /**
     * Retrieves single creature based on id given. If creature doesn't exist, returns null.
     * @param id id of creature
     * @return creature with given id, null if no creature with given id exists
     */
    Creature getCreature(Long id);
}
