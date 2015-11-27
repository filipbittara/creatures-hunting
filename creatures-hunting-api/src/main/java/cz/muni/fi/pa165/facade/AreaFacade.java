package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.AreaDTO;
import cz.muni.fi.pa165.persistence.entity.Creature;

import java.util.List;

/**
 * @author Filip Bittara
 */
public interface AreaFacade {
    public Long createArea(AreaDTO newArea);
    public void updateArea(AreaDTO area);
    public void deleteArea(AreaDTO area);
    public List<AreaDTO> getAllAreas();
    public List<AreaDTO> getAreasForCreature(Creature creature);
    public AreaDTO getArea(Long id);
    public void addCreatureToArea(Long creatureId, Long areaId);
    public void removeCreatureFromArea(Long creatureId, Long areaId);
}
