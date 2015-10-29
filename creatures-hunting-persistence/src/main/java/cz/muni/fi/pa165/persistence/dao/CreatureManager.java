package cz.muni.fi.pa165.persistence.dao;

import cz.muni.fi.pa165.persistence.entity.Creature;
import java.util.List;

/**
 * Creatures data access object.
 *
 * @author Filip Bittara
 */
public interface CreatureManager {
    public Creature findCreature(Long id);
    public void addCreature(Creature creature);
    public void deleteCreature(Creature creature);
    public void updateCreature(Creature creature);
    public List<Creature> findAllCreatures();
}
