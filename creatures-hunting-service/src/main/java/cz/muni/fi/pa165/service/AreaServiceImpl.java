/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.persistence.dao.AreaManager;
import cz.muni.fi.pa165.persistence.dao.CreatureManager;
import cz.muni.fi.pa165.persistence.entity.Area;
import cz.muni.fi.pa165.persistence.entity.Creature;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;

/**
 *
 * @author Filip Bittara
 */

@Service
public class AreaServiceImpl implements AreaService {
    @Inject
    private CreatureManager creatureManager;
    
    @Inject
    private AreaManager areaManager;

    @Override
    public Area createArea(Area area) {
        areaManager.addArea(area);
        return area;
    }

    @Override
    public void updateArea(Area area) {
        areaManager.updateArea(area);
    }

    @Override
    public void deleteArea(Area area) {
        areaManager.deleteArea(area);
    }

    @Override
    public List<Area> getAllAreas() {
        return areaManager.findAllAreas();
    }

    @Override
    public Set<Area> getAreasForCreature(Creature creature) {
        return new HashSet<>(creature.getAreas());
    }

    @Override
    public Area getArea(Long id) {
        return areaManager.findArea(id);
    }

    @Override
    public void addCreatureToArea(Creature creature, Area area) {
        if (creature != null && area != null) {
            area.addCreature(creature);
        } else {
            throw new IllegalArgumentException();
        }
        area.addCreature(creature);
    }

    @Override
    public void removeCreatureFromArea(Creature creature, Area area) {
        area.removeCreature(creature);
    }
    
}
