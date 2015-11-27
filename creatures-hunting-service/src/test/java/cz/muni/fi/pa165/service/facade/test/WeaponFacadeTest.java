/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service.facade.test;

import cz.muni.fi.pa165.dto.CreatureDTO;
import cz.muni.fi.pa165.dto.WeaponDTO;
import cz.muni.fi.pa165.facade.WeaponFacade;
import cz.muni.fi.pa165.persistence.dao.CreatureManager;
import cz.muni.fi.pa165.persistence.dao.WeaponManager;
import cz.muni.fi.pa165.persistence.entity.Creature;
import cz.muni.fi.pa165.persistence.entity.Weapon;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.WeaponService;
import cz.muni.fi.pa165.service.configuration.ServiceConfiguration;
import cz.muni.fi.pa165.service.facade.WeaponFacadeImpl;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Filip Bittara
 */
@ContextConfiguration(classes=ServiceConfiguration.class)
public class WeaponFacadeTest extends AbstractTransactionalTestNGSpringContextTests {
    
    @Mock
    private WeaponService weaponService;
    
    private WeaponFacadeImpl weaponFacade;
    
    @Autowired
    private BeanMappingService beanMappingService;
    
    @org.testng.annotations.BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
        weaponFacade = new WeaponFacadeImpl();
        weaponFacade.setWeaponService(weaponService);
        weaponFacade.setBeanMappingService(beanMappingService);
        
    }
    
    private WeaponDTO weapon1;
    private WeaponDTO weapon2;
    private List<WeaponDTO> weapons;
    private CreatureDTO creature;
    
    @BeforeMethod
    public void prepareWeapons(){
        weapon1 = new WeaponDTO();
        weapon1.setName("Knife");
        weapon1.setAmmunition("none");
        weapon1.setGunReach(1.0);
        
        weapon2 = new WeaponDTO();
        weapon2.setName("Gun");
        weapon2.setAmmunition("bullets");
        weapon2.setGunReach(10.0);
        
        weapons = new ArrayList<WeaponDTO>();
        weapons.add(weapon1);
        weapons.add(weapon2);
        
        creature = new CreatureDTO();
        creature.setName("Slime");
        creature.setWeight(150.0);
        creature.setWeakness("Often slips on stairs");
    }
    /**
     * Checks if addWeapon() method of weapon facade implementation uses weapon relevant service method
     */
    @Test
    public void addWeaponTest() {
        Weapon result = beanMappingService.mapTo(weapon1, Weapon.class);
        result.setId(1l);
        when(weaponService.addWeapon(beanMappingService.mapTo(weapon1, Weapon.class))).thenReturn(result);
        weaponFacade.addWeapon(weapon1);
        verify(weaponService, times(1)).addWeapon(beanMappingService.mapTo(weapon1, Weapon.class));
    }
    /**
     * Checks if removeWeapon() method of weapon facade implementation uses weapon relevant service method
     */
    @Test
    public void deleteWeaponTest() {
        weaponFacade.removeWeapon(weapon1);
        verify(weaponService, times(1)).deleteWeapon(beanMappingService.mapTo(weapon1, Weapon.class));
    }
    
    /**
     * Checks if updateWeapon() method of weapon facade implementation uses weapon relevant service method
     */
    @Test
    public void updateWeaponTest() {
        weaponFacade.updateWeapon(weapon1);
        verify(weaponService, times(1)).updateWeapon(beanMappingService.mapTo(weapon1, Weapon.class));
    }
    
    /**
     * Checks if getAllWeapons() method of weapon facade implementation uses weapon relevant service method
     */
    @Test
    public void findAllWeaponsTest() {
        when(weaponService.findAllWeapons()).thenReturn(beanMappingService.mapTo(weapons, Weapon.class));
        weaponFacade.getAllWeapons();
        verify(weaponService, times(1)).findAllWeapons();
    }
    
    /**
     * Checks if getWeaponById() method of weapon facade implementation uses weapon relevant service method
     */
    @Test
    public void findWeaponTest() {
        when(weaponService.findWeaponById(1l)).thenReturn(beanMappingService.mapTo(weapon1, Weapon.class));
        weaponFacade.getWeaponById(1l);
        verify(weaponService, times(1)).findWeaponById(1l);      
    }
    
    /**
     * Checks if getWeaponByName() method of weapon facade implementation uses weapon relevant service method
     */
    @Test
    public void findWeaponByNameTest() {
        when(weaponService.findWeaponByName("Gun")).thenReturn(beanMappingService.mapTo(weapon2, Weapon.class));
        weaponFacade.getWeaponByName("Gun");
        verify(weaponService, times(1)).findWeaponByName("Gun");
    }
    
    /**
     * Checks if getWeaponsByCreature() method of weapon facade implementation uses weapon relevant service method
     */
    @Test
    public void getWeaponsByCreatureTest() {
        when(weaponService.findWeaponsByCreature(beanMappingService.mapTo(creature, Creature.class))).thenReturn(beanMappingService.mapTo(weapons, Weapon.class));
        weaponFacade.getWeaponsByCreature(creature);
        verify(weaponService, times(1)).findWeaponsByCreature(beanMappingService.mapTo(creature, Creature.class));
    }
    /**
     * Checks if assignCreature() method of weapon facade implementation uses weapon relevant service method
     */
    @Test
    public void assignCreatureTest() {
        weaponFacade.assignCreature(weapon1, creature);
        verify(weaponService, times(1)).assignCreature(beanMappingService.mapTo(weapon1, Weapon.class), beanMappingService.mapTo(creature, Creature.class));
    }
    
    /**
     * Checks if removeCreature() method of weapon facade implementation uses weapon relevant service method
     */
    @Test
    public void removeCreatureTest() {
        weaponFacade.removeCreature(weapon1, creature);
	verify(weaponService, times(1)).removeCreature(beanMappingService.mapTo(weapon1, Weapon.class), beanMappingService.mapTo(creature, Creature.class));	
    }
}
