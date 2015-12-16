package cz.muni.fi.pa165.service.facade.test;

import cz.muni.fi.pa165.dto.AreaDTO;
import cz.muni.fi.pa165.dto.CreatureDTO;
import cz.muni.fi.pa165.facade.AreaFacade;
import cz.muni.fi.pa165.persistence.entity.Area;
import cz.muni.fi.pa165.persistence.entity.Creature;
import cz.muni.fi.pa165.service.AreaService;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.CreatureService;
import cz.muni.fi.pa165.service.configuration.ServiceConfiguration;
import cz.muni.fi.pa165.service.facade.AreaFacadeImpl;
import cz.muni.fi.pa165.service.facade.AreaFacadeImpl;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.service.spi.ServiceException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;

import static org.mockito.Mockito.*;
import org.testng.annotations.Test;

/**
 *
 * @author Ondrej Klein
 */
@ContextConfiguration(classes=ServiceConfiguration.class)
public class AreaFacadeTest extends AbstractTransactionalTestNGSpringContextTests {
	@Mock
	private AreaService areaService;
	
	@Mock
	private CreatureService creatureService;
	
	private AreaFacadeImpl areaFacade;
	
	@Autowired
    private BeanMappingService beanMappingService;
	
	@org.testng.annotations.BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
        areaFacade = new AreaFacadeImpl();
        areaFacade.setAreaService(areaService);
		areaFacade.setCreatureService(creatureService);
        areaFacade.setBeanMappingService(beanMappingService);
    }
	
	private AreaDTO area1;
    private AreaDTO area2;
    private List<AreaDTO> areas;
    private CreatureDTO creature;
	
	@BeforeMethod
	public void prepareAreas() {
		area1 = new AreaDTO();
        area1.setName("AreaDTO1");
        area1.setLatitude(1.0);
        area1.setLongitude(1.0);

        area2 = new AreaDTO();
        area2.setName("Area2");
        area2.setLatitude(2.0);
        area2.setLongitude(2.0);

        areas = new ArrayList<>();
        areas.add(area1);
        areas.add(area2);

        creature = new CreatureDTO();
        creature.setName("Something big");
        creature.setFerocity(5);
        creature.setHeight(3.5);
        creature.setWeakness("It will probably injure its head when walking trough small door");
	}
	
	/**
     * Tests Area creation through facade layer
     */
	@Test
	public void createAreaTest() {
		Area res = beanMappingService.mapTo(area1, Area.class);
		res.setId(123l);
		when(areaService.createArea(beanMappingService.mapTo(area1, Area.class))).thenReturn(res);
		areaFacade.createArea(area1);
		verify(areaService, times(1)).createArea(beanMappingService.mapTo(area1, Area.class));
	}
	
	/**
     * Tests Area update through facade layer
     */
	@Test
	public void updateAreaTest() {
		areaFacade.updateArea(area1);
		verify(areaService, times(1)).updateArea(beanMappingService.mapTo(area1, Area.class));
	}
	
	/**
     * Tests Area deletion through facade layer
     */
	@Test
	public void deleteAreaTest() {
		areaFacade.deleteArea(area1);
		verify(areaService, times(1)).deleteArea(beanMappingService.mapTo(area1, Area.class));
	}
	
	/**
     * Tests getting all areas through facade layer
     */
	@Test
	public void getAllAreasTest() {
		areaFacade.getAllAreas();
		verify(areaService, times(1)).getAllAreas();
	}
	
	/**
     * Tests getting areas in which creature has been spotted through facade layer
     */
	@Test
	public void getAreasForCreatureTest() {
		areaFacade.getAreaForCreature(beanMappingService.mapTo(creature, Creature.class));
		verify(areaService, times(1)).getAreaForCreature(beanMappingService.mapTo(creature, Creature.class));
	}
	
	/**
     * Tests getting area by id through facade layer
     */
	@Test
	public void getAreaTest() {
		when(areaService.getArea(123l)).thenReturn(beanMappingService.mapTo(area1, Area.class));
		areaFacade.getArea(123l);
		verify(areaService, times(1)).getArea(123l);
	}
	
	/**
     * Tests adding creature to area through facade layer
     */
	@Test
	public void addCreatureToAreaTest() {
		creature.setId(1l);
		area1.setId(2l);
		when(creatureService.getCreature(1l)).thenReturn(beanMappingService.mapTo(creature, Creature.class));
		when(areaService.getArea(2l)).thenReturn(beanMappingService.mapTo(area1, Area.class));
		areaFacade.addCreatureToArea(creature.getId(), area1.getId());
		verify(areaService, times(1)).addCreatureToArea(beanMappingService.mapTo(creature, Creature.class), beanMappingService.mapTo(area1, Area.class));
	}
	
	/**
     * Tests removing creature from area through facade layer
     */
	@Test
	public void removeCreatureFromAreaTest() {
		creature.setId(1l);
		area1.setId(2l);
		when(creatureService.getCreature(1l)).thenReturn(beanMappingService.mapTo(creature, Creature.class));
		when(areaService.getArea(2l)).thenReturn(beanMappingService.mapTo(area1, Area.class));
		areaFacade.removeCreatureFromArea(creature.getId(), area1.getId());
		verify(areaService, times(1)).removeCreatureFromArea(beanMappingService.mapTo(creature, Creature.class), beanMappingService.mapTo(area1, Area.class));
	}

	
	
}
