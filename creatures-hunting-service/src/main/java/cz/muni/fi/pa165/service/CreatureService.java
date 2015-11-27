package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.persistence.entity.Creature;
import java.util.List;
import java.util.Set;

/**
 * @author Filip Bittara
 */
public interface CreatureService {
    Creature createCreature(Creature creature);
    void updateCreature(Creature creature);
    void deleteCreature(Long creatureId);
    List<Creature> getAllCreatures();
    Set<Creature> getCreaturesInCircle(double latitude, double longitude, double radius);
    Set<Creature> getCreaturesByArea(String areaName);
    Set<Creature> getCreaturesByWeapon(String weaponName);
    Creature getCreature(Long id);
}
