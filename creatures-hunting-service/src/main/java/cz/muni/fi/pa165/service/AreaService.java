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
    public Area createArea(Area area);
    public void updateArea(Area area);
    public void deleteArea(Long areaId);
    public List<Area> getAllAreas();
    public List<Area> getAreasForCreature(String creatureName);
    public Area getArea(Long id);
    public void addCreatureToArea(Creature creature, Area area);
    public void removeCreatureFromArea(Creature creature, Area area);
}
