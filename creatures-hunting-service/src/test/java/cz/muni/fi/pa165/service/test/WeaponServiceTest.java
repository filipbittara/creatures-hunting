/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service.test;

import cz.muni.fi.pa165.persistence.dao.CreatureManager;
import cz.muni.fi.pa165.persistence.dao.WeaponManager;
import cz.muni.fi.pa165.persistence.entity.Creature;
import cz.muni.fi.pa165.persistence.entity.Weapon;
import cz.muni.fi.pa165.service.WeaponService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.service.spi.ServiceException;
import org.junit.Assert;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.mockito.Mockito.*;

/**
 * @author Filip Bittara
 */
@ContextConfiguration(classes=ServiceConfiguration.class)
public class WeaponServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Mock
    private WeaponManager weaponManager;
    
    @Mock
    private CreatureManager creatureManager;
    
    @Autowired
    @InjectMocks
    private WeaponService weaponService;
    
    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }
    
    private Weapon weapon1;
    private Weapon weapon2;
    private List<Weapon> weapons;
    private Creature creature;
    
    @BeforeMethod
    public void prepareWeapons(){
        weapon1 = new Weapon();
        weapon1.setName("Knife");
        weapon1.setAmmunition("none");
        weapon1.setGunReach(1.0);
        
        weapon2 = new Weapon();
        weapon2.setName("Gun");
        weapon2.setAmmunition("bullets");
        weapon2.setGunReach(10.0);
        
        weapons = new ArrayList<Weapon>();
        weapons.add(weapon1);
        weapons.add(weapon2);
        
        creature = new Creature();
        creature.setName("Something big");
        creature.setFerocity(5);
        creature.setHeight(3.5);
        creature.setWeakness("It will probably injure its head when walking trough small door");
    }
    
    @Test
    public void addWeaponTest() {
        weaponService.addWeapon(weapon1);
        verify(weaponManager, times(1)).addWeapon(weapon1);
    }
    
    @Test
    public void deleteWeaponTest() {
        weaponService.deleteWeapon(weapon1);
        verify(weaponManager, times(1)).deleteWeapon(weapon1);
    }
    
    @Test
    public void updateWeaponTest() {
        weaponService.addWeapon(weapon1);
        verify(weaponManager, times(1)).addWeapon(weapon1);
        weapon1.setName("Rifle");
        weapon1.setGunReach(15.0);
        weaponService.updateWeapon(weapon1);
        verify(weaponManager, times(1)).updateWeapon(weapon1);
    }
    
    @Test
    public void findAllWeaponsTest() {
        weaponService.findAllWeapons();
        verify(weaponManager, times(1)).findAllWeapons();
    }
    
    @Test
    public void findWeaponTest() {
        weaponService.addWeapon(weapon1);
        weaponService.addWeapon(weapon2);
        
        verify(weaponManager, times(1)).addWeapon(weapon2);
        verify(weaponManager, times(1)).addWeapon(weapon2);
        
        when(weaponManager.findAllWeapons()).thenReturn(weapons);
        
        Assert.assertEquals(weaponManager.findAllWeapons(), weapons);
        verify(weaponManager, times(1)).findAllWeapons();
    }
    
    @Test
    public void assignWeaponCreatureTest() {
        weaponService.assignCreature(weapon1, creature);
        Assert.assertTrue(weapon1.getCreatures().contains(creature));
    }
    
    @Test
    public void removeWeaponCreatureTest() {
        weaponService.removeCreature(weapon1, creature);
        Assert.assertFalse(weapon1.getCreatures().contains(creature));
    }
    
    @Test
    public void findWeaponsForCreatureTest() {
        weaponService.assignCreature(weapon1, creature);
        weaponService.assignCreature(weapon2, creature);
        Assert.assertTrue(weaponService.getWeaponsForCreature().contains(weapon1));
        Assert.assertTrue(weaponService.getWeaponsForCreature().contains(weapon2));
    }
    
}
