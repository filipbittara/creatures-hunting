/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.persistence.entity.Area;
import cz.muni.fi.pa165.persistence.entity.Creature;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Filip Bittara
 */
public interface AreaService {
    Area createArea(Area area);
    void updateArea(Area area);
    void deleteArea(Area area);
    List<Area> getAllAreas();
    Set<Area> getAreasForCreature(Creature creature);
    Area getArea(Long id);
    void addCreatureToArea(Creature creature, Area area);
    void removeCreatureFromArea(Creature creature, Area area);
    void assignCreature(Area area, Creature creature);
}
