package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.persistence.entity.Creature;
import java.util.List;

/**
 * @author Filip Bittara
 */
public interface CreatureService {
    public Creature createCreature(Creature creature);
    public void updateCreature(Creature creature);
    public void deleteCreature(Long creatureId);
    public List<Creature> getAllCreatures();
    public List<Creature> getCreaturesInCircle(double latitude, double longitude, double radius);
    public List<Creature> getCreaturesByArea(String areaName);
    public List<Creature> getCreaturesByWeapon(String weaponName);
    public Creature getCreature(Long id);
}
