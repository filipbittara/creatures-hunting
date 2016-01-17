package cz.muni.fi.pa165.persistence.test;

import cz.muni.fi.pa165.persistence.PersistenceApplicationContext;
import cz.muni.fi.pa165.persistence.dao.AreaManager;
import cz.muni.fi.pa165.persistence.dao.CreatureManager;
import cz.muni.fi.pa165.persistence.entity.Area;
import cz.muni.fi.pa165.persistence.entity.Creature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * Test class to test area manager functionality.
 * @author Filip Bittara
 */
@ContextConfiguration(classes=PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class AreaManagerTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private AreaManager areaManager;
    
    @Autowired
    private CreatureManager creatureManager;
    
    @PersistenceContext 
    private EntityManager em;
    
    /**
     * Checks insert to DB as well as obtaining all elements from it.
     */
    @Test
    public void findAll(){
        Area a1 = new Area();
        Area a2 = new Area();
        a1.setName("area 1");
        a2.setName("area 2");

        areaManager.addArea(a1);
        areaManager.addArea(a2);

        List<Area> areas  = areaManager.findAllAreas();

        Assert.assertEquals(areas.size(), 2);
        Assert.assertTrue(areas.contains(a1));
        Assert.assertTrue(areas.contains(a2));

    }

    /**
     * Checks that entity with null name cannot be saved.
     */
    @Test(expectedExceptions=ConstraintViolationException.class)
    public void nullAreaNameNotAllowed(){
            Area a = new Area();
            a.setName(null);
            areaManager.addArea(a);		
    }

    /**
     * Checks that entity name must be unique.
     */
    @Test(expectedExceptions=PersistenceException.class)
    public void nameIsUnique(){
            Area a = new Area();
            a.setName("Area 1");
            areaManager.addArea(a);	
            a = new Area();
            a.setName("Area 1");
            areaManager.addArea(a);
    }

    /**
     * Checks that name is saved properly.
     */
    @Test()
    public void savesName(){
            Area a = new Area();
            a.setName("Area 1");
            areaManager.addArea(a);
            Assert.assertEquals(areaManager.findArea(a.getId()).getName(), "Area 1");
    }

    /**
     * Checks that entity could be removed.
     */
    @Test()
    public void delete(){
            Area a = new Area();
            a.setName("Area 1");
            areaManager.addArea(a);
            Assert.assertNotNull(areaManager.findArea(a.getId()));
            areaManager.deleteArea(a);
            Assert.assertNull(areaManager.findArea(a.getId()));
    }
    
    /**
     * Testing of deleting null entity
     */
    @Test(expectedExceptions=IllegalArgumentException.class)
    public void deleteNull() {
        areaManager.deleteArea(null);
    }
    
    /**
     * Checks that entity could be updated.
     */
    @Test
    public void update(){
            Area a = new Area();
            a.setName("Area 1");
            areaManager.addArea(a);
            Assert.assertEquals(areaManager.findArea(a.getId()).getName(), "Area 1");
            a.setName("Other area");
            areaManager.updateArea(a);
            Assert.assertEquals(areaManager.findArea(a.getId()).getName(), "Other area");
    }
    
    /**
     * Testing of updating null entity
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateNull() {
        areaManager.updateArea(null);
    }
    
    /**
     * Checks that assigning area to creature works.
     */
    @Test
    public void creaturesInArea(){
        Area a = new Area();
        a.setName("Area 1");
        areaManager.addArea(a);
        Creature c = new Creature();
        c.setName("Creature 1");
        creatureManager.addCreature(c);
        a.addCreature(c);
        Creature found = creatureManager.findCreature(c.getId());
        Assert.assertEquals(found.getAreas().size(), 1);
        Assert.assertEquals(found.getAreas().iterator().next().getName(), "Area 1");
    }
    /**
     * Checks that finding area by id
     */
    @Test
    public void findAreaTest() {
        Area a = new Area();
        a.setName("Area 1");
        areaManager.addArea(a);
        Area found = areaManager.findArea(a.getId());
        Assert.assertEquals(found, a);
    }

    /**
     * Checks that finding area by name is working.
     */
    @Test
    public void findAreaByNameTest(){
        Area a = new Area();
        a.setName("Area");
        areaManager.addArea(a);

        Area result = areaManager.findAreaByName("Area");

        Assert.assertEquals(result.getId(), a.getId());
        Assert.assertEquals(result.getName(), a.getName());
        Assert.assertEquals(result.getDescription(), a.getDescription());
        Assert.assertEquals(result.getCreatures(), a.getCreatures());
        Assert.assertEquals(result.getLatitude(), a.getLatitude());
        Assert.assertEquals(result.getLongitude(), a.getLongitude());
    }
    
    /**
     * Checks that finding area by null name throws exception.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findAreaNullName () {
        areaManager.findAreaByName(null);
    }
    
    /**
     * Checks that finding area by empty name throws exception.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findAreaEmptyName () {
        areaManager.findAreaByName("");
    }
}

