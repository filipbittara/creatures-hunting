package cz.muni.fi.pa165.persistence.test;

import cz.muni.fi.pa165.persistence.PersistenceApplicationContext;
import cz.muni.fi.pa165.persistence.dao.AreaManager;
import cz.muni.fi.pa165.persistence.dao.CreatureManager;
import cz.muni.fi.pa165.persistence.entity.Area;
import cz.muni.fi.pa165.persistence.entity.Creature;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
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

    @Test(expectedExceptions=ConstraintViolationException.class)
    public void nullAreaNameNotAllowed(){
            Area a = new Area();
            a.setName(null);
            areaManager.addArea(a);		
    }

    @Test(expectedExceptions=PersistenceException.class)
    public void nameIsUnique(){
            Area a = new Area();
            a.setName("Area 1");
            areaManager.addArea(a);	
            a = new Area();
            a.setName("Area 1");
            areaManager.addArea(a);
    }

    @Test()
    public void savesName(){
            Area a = new Area();
            a.setName("Area 1");
            areaManager.addArea(a);
            Assert.assertEquals(areaManager.findArea(a.getId()).getName(), "Area 1");
    }

    @Test()
    public void delete(){
            Area a = new Area();
            a.setName("Area 1");
            areaManager.addArea(a);
            Assert.assertNotNull(areaManager.findArea(a.getId()));
            areaManager.deleteArea(a);
            Assert.assertNull(areaManager.findArea(a.getId()));
    }

    @Test
    public void creaturesInArea(){
            Area a = new Area();
            a.setName("Area 1");
            areaManager.addArea(a);
            Creature c = new Creature();
            c.setName("Creature 1");
            a.addCreature(c);		
            Creature found = creatureManager.findCreature(c.getId());
            Assert.assertEquals(found.getAreas().size(), 1);
            Assert.assertEquals(found.getAreas().iterator().next().getName(), "Area 1");
    }
}
