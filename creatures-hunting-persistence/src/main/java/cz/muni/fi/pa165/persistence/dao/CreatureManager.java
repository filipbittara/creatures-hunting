package cz.muni.fi.pa165.persistence.dao;

import cz.muni.fi.pa165.persistence.entity.Creature;
import java.util.List;

/**
 * Creatures data access object.
 * For reference model see project's github wiki (https://github.com/kizivat/creatures-hunting/wiki). 
 * @author Filip Bittara
 */
public interface CreatureManager {
    /**
     * Finds and returns creature with selected id.
     * @param id id of wanted creature
     * @return creature with given id
     */
    public Creature findCreature(Long id);
    
    /**
     * Saves given creature.
     * @param creature creature to be saved
     */
    public void addCreature(Creature creature);
    /**
     * Removes given creature.
     * @param creature creature to be removed
     */
    public void deleteCreature(Creature creature);
    
    /**
     * Updates given creature, only if already exists.
     * @param creature creature to be updated
     */
    public void updateCreature(Creature creature);
    
    /**
     * Returns list of all creatures.
     * @return list of all creatures
     */
    public List<Creature> findAllCreatures();

    /**
     * Searches for creature with given name.
     *
     * @param name name of the creature to be searched for
     * @return instance of the creature with the given name
     */
    public Creature findCreatureByName(String name);
}
