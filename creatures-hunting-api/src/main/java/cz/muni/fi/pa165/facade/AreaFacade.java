package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.AreaDTO;
import java.util.List;

/**
 * @author Filip Bittara
 */
public interface AreaFacade {
    public Long createArea(AreaDTO newArea);
    public void updateArea(AreaDTO area);
    public void deleteArea(Long areaId);
    public List<AreaDTO> getAllAreas();
    public List<AreaDTO> getAreasForCreature(String creatureName);
    public AreaDTO getArea(Long id);
    public void addCreatureToArea(Long creatureId, Long areaId);
    public void removeCreatureFromArea(Long creatureId, Long areaId);
}
