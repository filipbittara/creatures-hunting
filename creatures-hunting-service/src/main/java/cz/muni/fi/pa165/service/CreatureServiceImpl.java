package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.persistence.dao.AreaManager;
import cz.muni.fi.pa165.persistence.dao.CreatureManager;
import cz.muni.fi.pa165.persistence.dao.WeaponManager;
import cz.muni.fi.pa165.persistence.entity.Area;
import cz.muni.fi.pa165.persistence.entity.Creature;
import cz.muni.fi.pa165.service.exception.ManagerDataAccessException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;

/**
 * @author Filip Bittara
 */

@Service
public class CreatureServiceImpl implements CreatureService {
    @Inject
    private CreatureManager creatureManager;
    
    @Inject
    private AreaManager areaManager;
    
    @Inject
    private WeaponManager weaponManager;
    
    @Override
    public Creature createCreature(Creature creature) {
        try {
            creatureManager.addCreature(creature);
        } catch (Exception e) {
            throw new ManagerDataAccessException("Error while creating creature", e);
        }
        return creature;
    }

    @Override
    public void updateCreature(Creature creature) {
        try {
            creatureManager.updateCreature(creature);
        } catch (Exception e) {
            throw new ManagerDataAccessException("Error while updating creature", e);
        }
    }

    @Override
    public void deleteCreature(Long creatureId) {
        try {
            creatureManager.deleteCreature(creatureManager.findCreature(creatureId));
        } catch (Exception e) {
            throw new ManagerDataAccessException("Error while removing creature", e);
        }
    }

    @Override
    public List<Creature> getAllCreatures() {
        try { 
            return creatureManager.findAllCreatures();
        } catch (Exception e) {
            throw new ManagerDataAccessException("Error while retireving creatures", e);
        }
    }

    @Override
    public Set<Creature> getCreaturesInCircle(double latitude, double longitude, double radius) {
        Set<Creature> result = new HashSet<>();
        try {
            for(Area area : areaManager.findAllAreas()) {
                double distance = Math.sqrt(Math.pow(latitude - area.getLatitude(), 2) + Math.pow(longitude - area.getLongitude(),2));
                if(distance <= radius ) {
                    for(Creature creature : area.getCreatures()) {
                        result.add(creature);
                    }
                }
            }
        } catch (Exception e) {
            throw new ManagerDataAccessException("Error while retireving creatures", e);
        }
        return result;
    }

    @Override
    public Set<Creature> getCreaturesByArea(String areaName) {
        Area area = null;
        try {
            area = areaManager.findAreaByName(areaName);
        } catch (Exception e) {
            throw new ManagerDataAccessException("Error while retireving creatures", e);
        }
        if (area != null)
            return new HashSet<>(area.getCreatures());
        return null;
    }

    @Override
    public Set<Creature> getCreaturesByWeapon(String weaponName) {
        try {
            return new HashSet<>(weaponManager.findWeaponByName(weaponName).getCreatures());
        } catch (Exception e) {
            throw new ManagerDataAccessException("Error while retireving creatures", e);
        }
    }

    @Override
    public Creature getCreature(Long id) {
        try {
            return creatureManager.findCreature(id);
        } catch (Exception e) {
            throw new ManagerDataAccessException("Error while retireving creature", e);
        }
    }
    
}
