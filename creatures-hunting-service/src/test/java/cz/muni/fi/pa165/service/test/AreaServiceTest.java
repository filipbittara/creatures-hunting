package cz.muni.fi.pa165.service.test;

import cz.muni.fi.pa165.persistence.dao.AreaManager;
import cz.muni.fi.pa165.persistence.dao.CreatureManager;
import cz.muni.fi.pa165.persistence.entity.Area;
import cz.muni.fi.pa165.persistence.entity.Creature;
import cz.muni.fi.pa165.service.AreaService;
import cz.muni.fi.pa165.service.configuration.ServiceConfiguration;
import org.hibernate.service.spi.ServiceException;
import org.junit.Assert;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;

/**
 * @author David Kizivat
 */
@ContextConfiguration(classes=ServiceConfiguration.class)
public class AreaServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Mock
    private AreaManager areaManager;

    @Mock
    private CreatureManager creatureManager;

    @Autowired
    @InjectMocks
    private AreaService areaService;

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    private Area area1;
    private Area area2;
    private List<Area> areas;
    private Creature creature;

    @BeforeMethod
    public void prepareAreas(){
        area1 = new Area();
        area1.setName("Area1");
        area1.setLatitude(1.0);
        area1.setLongitude(1.0);

        area2 = new Area();
        area2.setName("Area2");
        area2.setLatitude(2.0);
        area2.setLongitude(2.0);

        areas = new ArrayList<>();
        areas.add(area1);
        areas.add(area2);

        creature = new Creature();
        creature.setName("Something big");
        creature.setFerocity(5);
        creature.setHeight(3.5);
        creature.setWeakness("It will probably injure its head when walking trough small door");
    }

    /**
     * Checks if createArea() method of area service uses area manager correctly.
     */
    @Test
    public void addAreaTest() {
        areaService.createArea(area1);
        verify(areaManager, times(1)).addArea(area1);
    }

    /**
     * Checks if deleteArea() method of area service uses area manager correctly.
     */
    @Test
    public void deleteAreaTest() {
        areaService.createArea(area1);
        areaService.deleteArea(area1);

        verify(areaManager, times(1)).deleteArea(area1);
    }

    /**
     * Checks if updateArea() method of area service uses area manager correctly.
     */
    @Test
    public void updateAreaTest() {
        areaService.createArea(area1);
        verify(areaManager, atLeastOnce()).addArea(area1);
        area1.setName("Area3");
        area1.setDescription("desc");
        area1.setLatitude(3.0);
        area1.setLongitude(3.0);
        areaService.updateArea(area1);


        verify(areaManager, times(1)).updateArea(area1);
    }

    /**
     * Checks if findAllAreas() method of area service uses area manager correctly.
     */
    @Test
    public void findAllAreasTest() {
        areaService.getAllAreas();
        verify(areaManager, times(1)).findAllAreas();
    }

    /**
     * Checks if findArea() method of area service uses area manager correctly.
     */
    @Test
    public void findAreaTest() {
        areaService.createArea(area1);
        verify(areaManager, atLeastOnce()).addArea(area1);

        areaService.createArea(area2);
        verify(areaManager, times(1)).addArea(area2);

        when(areaManager.findAllAreas()).thenReturn(areas);

        Assert.assertEquals(areaManager.findAllAreas(), areas);
        verify(areaManager, atLeast(2)).findAllAreas();
    }

    /**
     * Checks if all areas are retrieved correctly for given creature.
     */
    @Test
    public void getAreaForCreatureTest() {
        areaService.addCreatureToArea(creature, area1);
        Area area = areaService.getAreaForCreature(creature);
        Assert.assertEquals(area, area1);

    }


}

