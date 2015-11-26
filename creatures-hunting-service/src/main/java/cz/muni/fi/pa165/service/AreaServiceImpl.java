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
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Filip Bittara
 */
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
    public void deleteArea(Long areaId) {
        areaManager.deleteArea(areaManager.findArea(areaId));
    }

    @Override
    public List<Area> getAllAreas() {
        return areaManager.findAllAreas();
    }

    @Override
    public List<Area> getAreasForCreature(String creatureName) {
        return new ArrayList<Area>(creatureManager.findCreatureByName(creatureName).getAreas());
    }

    @Override
    public Area getArea(Long id) {
        return areaManager.findArea(id);
    }

    @Override
    public void addCreatureToArea(Creature creature, Area area) {
        area.addCreature(creature);
    }

    @Override
    public void removeCreatureFromArea(Creature creature, Area area) {
        area.removeCreature(creature);
    }
    
}
