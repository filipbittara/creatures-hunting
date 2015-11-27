/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.persistence.entity.Area;
import cz.muni.fi.pa165.persistence.entity.Creature;
import java.util.List;

/**
 *
 * @author Filip Bittara
 */
public interface AreaService {
    Area createArea(Area area);
    void updateArea(Area area);
    void deleteArea(Long areaId);
    List<Area> getAllAreas();
    List<Area> getAreasForCreature(String creatureName);
    Area getArea(Long id);
    void addCreatureToArea(Creature creature, Area area);
    void removeCreatureFromArea(Creature creature, Area area);
}
