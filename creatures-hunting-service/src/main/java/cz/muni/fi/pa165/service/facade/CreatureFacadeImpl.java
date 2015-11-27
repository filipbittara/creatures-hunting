/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.dto.CreatureDTO;
import cz.muni.fi.pa165.facade.CreatureFacade;
import cz.muni.fi.pa165.persistence.entity.Creature;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.CreatureService;
import java.util.List;
import javax.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Filip Bittara
 */

@Service
@Transactional
public class CreatureFacadeImpl implements CreatureFacade {

    @Inject
    private CreatureService creatureService;

    @Autowired
    private BeanMappingService beanMappingService;
    
    @Override
    public Long createCreature(CreatureDTO newCreature) {
        Creature creature = creatureService.createCreature(beanMappingService.mapTo(newCreature, Creature.class));
        return creature.getId();
    }

    @Override
    public void updateCreature(CreatureDTO creature) {
        creatureService.updateCreature(beanMappingService.mapTo(creature, Creature.class));
    }

    @Override
    public void deleteCreature(Long creatureId) {
         creatureService.deleteCreature(creatureId);
    }

    @Override
    public List<CreatureDTO> getAllCreatures() {
        return beanMappingService.mapTo(creatureService.getAllCreatures(), CreatureDTO.class);
    }

    @Override
    public List<CreatureDTO> getCreaturesInCircle(double latitude, double longitude, double radius) {
        return beanMappingService.mapTo(creatureService.getCreaturesInCircle(latitude, longitude, radius), CreatureDTO.class);
    }

    @Override
    public List<CreatureDTO> getCreaturesByArea(String areaName) {
        return beanMappingService.mapTo(creatureService.getCreaturesByArea(areaName), CreatureDTO.class);}

    @Override
    public List<CreatureDTO> getCreaturesByWeapon(String weaponName) {
        return beanMappingService.mapTo(creatureService.getCreaturesByWeapon(weaponName), CreatureDTO.class);
    }

    @Override
    public CreatureDTO getCreature(Long id) {
        return beanMappingService.mapTo(creatureService.getCreature(id), CreatureDTO.class);
    }
    
}
