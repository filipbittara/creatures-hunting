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
import cz.muni.fi.pa165.service.exception.ManagerDataAccessException;
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
        try {
            areaManager.addArea(area);
        } catch (Exception e) {
            throw new ManagerDataAccessException("Error while creating area", e);
        }
        return area;
    }

    @Override
    public void updateArea(Area area) {
        try {
            areaManager.updateArea(area);
        } catch (Exception e) {
            throw new ManagerDataAccessException("Error while updating area", e);
        }
    }

    @Override
    public void deleteArea(Area area) {
        try {
            areaManager.deleteArea(area);
        } catch (Exception e) {
            throw new ManagerDataAccessException("Error while removing area", e);
        }
    }

    @Override
    public List<Area> getAllAreas() {
        try {
            return areaManager.findAllAreas();
        } catch (Exception e) {
            throw new ManagerDataAccessException("Error while retrieving areas", e);
        }
    }

    @Override
    public Set<Area> getAreasForCreature(Creature creature) {
        return new HashSet<>(creature.getAreas());
    }

    @Override
    public Area getArea(Long id) {
        try {
            return areaManager.findArea(id);
        } catch (Exception e) {
            throw new ManagerDataAccessException("Error while retrieving area", e);
        }
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
