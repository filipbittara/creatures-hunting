package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.dto.AreaDTO;
import cz.muni.fi.pa165.facade.AreaFacade;
import cz.muni.fi.pa165.persistence.entity.Area;
import cz.muni.fi.pa165.service.AreaService;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.CreatureService;
import java.util.List;
import javax.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Filip Bittara
 */

@Service
@Transactional
public class AreaFacadeImpl implements AreaFacade{

    @Inject
    private AreaService areaService;
    
    @Inject
    private CreatureService creatureService;

    @Autowired
    private BeanMappingService beanMappingService;
    
    @Override
    public Long createArea(AreaDTO area) {
        return areaService.createArea(beanMappingService.mapTo(area, Area.class)).getId();
    }

    @Override
    public void updateArea(AreaDTO area) {
        areaService.updateArea(beanMappingService.mapTo(area, Area.class));
    }

    @Override
    public void deleteArea(Long areaId) {
        areaService.deleteArea(areaId);
    }

    @Override
    public List<AreaDTO> getAllAreas() {
        return beanMappingService.mapTo(areaService.getAllAreas(), AreaDTO.class);
    }

    @Override
    public List<AreaDTO> getAreasForCreature(String creatureName) {
        return beanMappingService.mapTo(areaService.getAreasForCreature(creatureName), AreaDTO.class);
    }

    @Override
    public AreaDTO getArea(Long id) {
        return beanMappingService.mapTo(areaService.getArea(id), AreaDTO.class);
    }

    @Override
    public void addCreatureToArea(Long creatureId, Long areaId) {
        areaService.addCreatureToArea(creatureService.getCreature(creatureId), areaService.getArea(areaId));
    }

    @Override
    public void removeCreatureFromArea(Long creatureId, Long areaId) {
        areaService.removeCreatureFromArea(creatureService.getCreature(creatureId), areaService.getArea(areaId));
    }
    
}
