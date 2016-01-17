/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.persistence.test;

import cz.muni.fi.pa165.persistence.InMemoryDatabaseSpring;
import cz.muni.fi.pa165.persistence.PersistenceApplicationContext;
import cz.muni.fi.pa165.persistence.dao.CreatureManager;
import cz.muni.fi.pa165.persistence.dao.WeaponManager;
import cz.muni.fi.pa165.persistence.entity.Weapon;
import cz.muni.fi.pa165.persistence.entity.enums.AmmunitionType;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test class for WeaponManager
 *
 * @author Ondrej Klein
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class WeaponManagerTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    public EntityManager em;

    @Autowired
    public WeaponManager wm;

    @Autowired
    public CreatureManager cm;

    private Weapon w1;
    private Weapon w2;

    private String wName1;
    private String wName2;

    /**
     * Fill the DB prior to tests
     */
    @BeforeMethod
    public void createWeapons() {
        wName1 = "shotgun1";
        wName2 = "machete2";

        w1 = new Weapon();
        w2 = new Weapon();

        w1.setName(wName1);
        w2.setName(wName2);

        w1.setGunReach(Double.valueOf(25.3));
        w2.setGunReach(Double.valueOf(0.9));

        w1.setAmmunition(AmmunitionType.SHELL);
        w2.setAmmunition(AmmunitionType.NONE);

        wm.addWeapon(w1);
        wm.addWeapon(w2);
    }

    /**
     * Test returning all entries
     */
    @Test
    public void findAll() {
        List<Weapon> weapons = wm.findAllWeapons();
        Assert.assertEquals(weapons.size(), 2);
        Assert.assertTrue(weapons.contains(w1));
        Assert.assertTrue(weapons.contains(w2));

    }

    /**
     * Tests adding Weapon entry
     */
    @Test
    public void addWeapon() {
        Weapon w3 = new Weapon();
        w3.setName("test");
        w3.setAmmunition(AmmunitionType.OTHER);
        w3.setGunReach(Double.MAX_VALUE);
        wm.addWeapon(w3);
        Weapon testWeapon = wm.findWeapon(w3.getId());
        Assert.assertEquals(testWeapon, w3);
    }

    /**
     * Test throwing exception when adding null
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addNull() {
        wm.addWeapon(null);
    }

    /**
     * Test adding Weapon with null name
     */
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void nullAreaNameNotAllowed() {
        Weapon w3 = new Weapon();
        w3.setName(null);
        wm.addWeapon(w3);
    }

    /**
     * Tests adding Weapon with already existing name
     */
    @Test(expectedExceptions = PersistenceException.class)
    public void notUniqueName() {
        Weapon w3 = new Weapon();
        w3.setName(wName1);
        wm.addWeapon(w3);
    }

    /**
     * Test of deleting a Weapon
     */
    @Test
    public void deleteWeapon() {
        Assert.assertNotNull(wm.findWeapon(w1.getId()));
        wm.deleteWeapon(wm.findWeapon(w1.getId()));
        Assert.assertNull(wm.findWeapon(w1.getId()));
    }

    /**
     * Test of deleting null
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void deleteNull() {
        wm.deleteWeapon(null);
    }

    /**
     * Test of updating Weapon's attributes
     */
    @Test
    public void updateWeapon() {
        Assert.assertEquals(wm.findWeapon(w1.getId()).getName(), wName1);
        String newName = "newName";
        w1.setName(newName);
        wm.updateWeapon(w1);
        Assert.assertEquals(wm.findWeapon(w1.getId()).getName(), newName);
    }

    /**
     * Test updating with null instead of weapon
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateNull() {
        wm.updateWeapon(null);
    }

    /**
     * Test updating weapon with null name
     */
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void updateWithNullName() {
        Assert.assertEquals(wm.findWeapon(w1.getId()).getName(), wName1);
        w1.setName(null);
        wm.updateWeapon(w1);
    }

    /**
     * Test updating weapon with non-unique name
     */
    @Test(expectedExceptions = javax.persistence.PersistenceException.class)
    public void updateWithNonUniqueName() {
        Assert.assertEquals(wm.findWeapon(w1.getId()).getName(), wName1);
        String newName = "machete2";
        w1.setName(newName);
        wm.updateWeapon(w1);

    }

}
