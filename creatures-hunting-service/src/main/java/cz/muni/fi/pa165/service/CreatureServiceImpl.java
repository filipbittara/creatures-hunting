package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.persistence.dao.AreaManager;
import cz.muni.fi.pa165.persistence.dao.CreatureManager;
import cz.muni.fi.pa165.persistence.dao.WeaponManager;
import cz.muni.fi.pa165.persistence.entity.Area;
import cz.muni.fi.pa165.persistence.entity.Creature;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
        creatureManager.addCreature(creature);
        return creature;
    }

    @Override
    public void updateCreature(Creature creature) {
        creatureManager.updateCreature(creature);
    }

    @Override
    public void deleteCreature(Long creatureId) {
        creatureManager.deleteCreature(creatureManager.findCreature(creatureId));
    }

    @Override
    public List<Creature> getAllCreatures() {
        return creatureManager.findAllCreatures();
    }

    @Override
    public List<Creature> getCreaturesInCircle(double latitude, double longitude, double radius) {
        List<Creature> result = new ArrayList<Creature>();
        for(Area area : areaManager.findAllAreas()) {
            double distance = Math.sqrt(Math.pow(latitude - area.getLatitude(), 2) + Math.pow(longitude - area.getLongitude(),2));
            if(distance <= radius ) {
                for(Creature creature : area.getCreatures()) {
                    result.add(creature);
                }
            }
        }
        return result;
    }

    @Override
    public List<Creature> getCreaturesByArea(String areaName) {
        return new ArrayList<Creature>(areaManager.findAreaByName(areaName).getCreatures());
    }

    @Override
    public List<Creature> getCreaturesByWeapon(String weaponName) {
        return new ArrayList<Creature>(weaponManager.findWeaponByName(weaponName).getCreatures());
    }

    @Override
    public Creature getCreature(Long id) {
        return creatureManager.findCreature(id);
    }
    
}
