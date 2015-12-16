package cz.muni.fi.pa165.persistence.test;

import cz.muni.fi.pa165.persistence.PersistenceApplicationContext;
import cz.muni.fi.pa165.persistence.dao.AreaManager;
import cz.muni.fi.pa165.persistence.dao.CreatureManager;
import cz.muni.fi.pa165.persistence.dao.WeaponManager;
import cz.muni.fi.pa165.persistence.entity.Area;
import cz.muni.fi.pa165.persistence.entity.Creature;
import cz.muni.fi.pa165.persistence.entity.Weapon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * Test class for DAO class CreatureManager.
 *
 * @author Kizivat
 */
@ContextConfiguration(classes=PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class CreatureManagerTest extends AbstractTestNGSpringContextTests {
    @PersistenceContext
    EntityManager em;

    @Autowired
    private CreatureManager cm;

    @Autowired
    private WeaponManager wm;

    @Autowired
    private AreaManager am;

    private Creature c1;
    private Creature c2;
    private Area a1;
    private Area a2;
    private Weapon w1;
    private Weapon w2;

    /**
     * Creates test objects.
     */
    @BeforeMethod
    public void beforeMethod() {
        a1 = new Area();
        a2 = new Area();
        a1.setName("Area1");
        a2.setName("Area2");
        am.addArea(a1);
        am.addArea(a2);


        w1 = new Weapon();
        w2 = new Weapon();
        w1.setName("Weapon1");
        w2.setName("Weapon2");
        wm.addWeapon(w1);
        wm.addWeapon(w2);

        c1 = new Creature();
        c2 = new Creature();

        c1.setName("Creature1");
        c1.setAgility(1);
        c1.setFerocity(1);
        c1.setHeight(1.1);
        c1.setWeight(1.1);
        c1.setWeakness("Weakness1");
        c1.setArea(a1);
        c1.addWeapon(w1);
        w1.addCreature(c1);

        c2.setName("Creature2");
    }

    /**
     * Tests findCreature() method.
     */
    @Test()
    public void findTest() {
        cm.addCreature(c1);

        Creature result = cm.findCreature(c1.getId());

        Assert.assertEquals(result, c1);
    }

    /**
     * Tests findAllCreatures() method.
     */
    @Test()
    public void findAllTest() {
        cm.addCreature(c1);
        cm.addCreature(c2);


        List<Creature> creatures = cm.findAllCreatures();

        Assert.assertEquals(creatures.size(), 2);
        Assert.assertTrue(creatures.contains(c1));
        Assert.assertTrue(creatures.contains(c2));
    }

    /**
     * Tests addition of a creature.
     */
    @Test()
    public void addTest() {
        cm.addCreature(c1);

        Creature result = cm.findCreature(c1.getId());

        Assert.assertNotNull(c1.getId());
        Assert.assertEquals(result, c1);
    }

    /**
     * Tests null creature addition.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addNullTest() {
        cm.addCreature(null);
    }

    /**
     * Tests addition of a creature with null name.
     */
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void addNullNameTest() {
        c1.setName(null);

        cm.addCreature(c1);
    }

    /**
     * Tests uniqueness of name of a creature.
     */
    @Test(expectedExceptions = PersistenceException.class)
    public void uniqueNameTest() {
        c2.setName("Creature1");

        cm.addCreature(c1);
        cm.addCreature(c2);
    }

    /**
     * Tests updateCreature() method.
     */
    @Test()
    public void updateTest() {
        cm.addCreature(c1);

        c1.setName("Creature3");
        c1.setAgility(3);
        c1.setFerocity(3);
        c1.setHeight(3.3);
        c1.setWeight(3.3);
        c1.setWeakness("Weakness3");
        c1.setArea(a1);
        c1.addWeapon(w2);
        w2.addCreature(c1);

        cm.updateCreature(c1);

        Creature result = cm.findCreature(c1.getId());

        Assert.assertEquals(result.getName(), "Creature3");
        Assert.assertEquals(result.getAgility(), new Integer(3));
        Assert.assertEquals(result.getFerocity(), new Integer(3));
        Assert.assertEquals(result.getHeight(), 3.3);
        Assert.assertEquals(result.getWeight(), 3.3);
        Assert.assertEquals(result.getWeakness(), "Weakness3");

        Assert.assertTrue(result.getArea().equals(a1));

        Assert.assertEquals(result.getWeapons().size(), 2);
        Assert.assertTrue(result.getWeapons().contains(w1));
        Assert.assertTrue(result.getWeapons().contains(w2));
    }

    /**
     * Tests null name update.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateNullNameTest() {
        cm.addCreature(c1);
        c2.setName(null);
        cm.updateCreature(c2);
    }

    /**
     * Tests deletion of a creature.
     */
    @Test()
    public void deleteTest() {
        Creature c = new Creature();
        c.setName("Creature1");

        cm.addCreature(c);

        Assert.assertNotNull(cm.findCreature(c.getId()));

        cm.deleteCreature(c);

        Assert.assertNull(cm.findCreature(c.getId()));
    }

    /**
     * Tests deletion of a null creature.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void deleteNullTest() {
        cm.deleteCreature(null);
    }

    /**
     * Checks that finding area by name is working.
     */
    @Test
    public void findCreatureByNameTest(){
        Creature a = new Creature();
        a.setName("Creature");
        cm.addCreature(a);

        Creature result = cm.findCreatureByName("Creature");

        Assert.assertEquals(result.getId(), a.getId());
        Assert.assertEquals(result.getName(), a.getName());
        Assert.assertEquals(result.getFerocity(), a.getFerocity());
        Assert.assertEquals(result.getHeight(), a.getHeight());
        Assert.assertEquals(result.getWeapons(), a.getWeapons());
        Assert.assertEquals(result.getAgility(), a.getAgility());
        Assert.assertEquals(result.getWeakness(), a.getWeakness());
        Assert.assertEquals(result.getWeight(), a.getWeight());
    }
}

