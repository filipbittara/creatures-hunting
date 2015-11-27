package cz.muni.fi.pa165.service.test;

import cz.muni.fi.pa165.persistence.dao.AreaManager;
import cz.muni.fi.pa165.persistence.dao.CreatureManager;
import cz.muni.fi.pa165.persistence.dao.WeaponManager;
import cz.muni.fi.pa165.persistence.entity.Area;
import cz.muni.fi.pa165.persistence.entity.Creature;
import cz.muni.fi.pa165.persistence.entity.Weapon;
import cz.muni.fi.pa165.service.CreatureService;
import cz.muni.fi.pa165.service.configuration.ServiceConfiguration;
import java.util.ArrayList;
import java.util.List;
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
import static org.mockito.Mockito.*;

/**
 * @author Ondrej Klein
 */
@ContextConfiguration(classes=ServiceConfiguration.class)
public class CreatureServiceTest extends AbstractTransactionalTestNGSpringContextTests {    
    @Mock
    private CreatureManager creatureManager;
	
	@Mock
	private AreaManager areaManager;
    
    @Autowired
    @InjectMocks
    private CreatureService creatureService;
	
	@BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }
	
	private Weapon weapon;
    private Creature creature1;
	private Creature creature2;
	private Creature creature3;
    private List<Area> areas;
	private List<Creature> creatures;
    private Area area;
	private Area area2;
    
    @BeforeMethod
    public void prepareWeapons(){
        weapon = new Weapon();
        weapon.setName("Chainsaw");
        weapon.setAmmunition("chainz");
        weapon.setGunReach(1.0);
        
        creature1 = new Creature();
        creature1.setName("Fire zombie");
        creature1.setFerocity(10);
        creature1.setHeight(2.2);
        creature1.setWeakness("Doesn't work when wet");
		
		creature2 = new Creature();
        creature2.setName("Knife thrower");
        creature2.setFerocity(3);
        creature2.setHeight(1.78);
        creature2.setWeakness("Brings knives into a gun fight");
		
		creature3 = new Creature();
        creature3.setName("Strangler");
        creature3.setFerocity(6);
        creature3.setHeight(3.14);
        creature3.setWeakness("Slow");
		
		creatures = new ArrayList<Creature>();
		creatures.add(creature1);
		creatures.add(creature2);
		creatures.add(creature3);
		
		area = new Area();
		area.setLatitude(5.0);
		area.setLongitude(5.0);
		
		area2 = new Area();
		area2.setLatitude(10.0);
		area2.setLongitude(10.0);
		
		areas = new ArrayList<>();
		areas.add(area);
		areas.add(area2);
		
    }
	
	@Test
	public void createCreatureTest() {
		creatureService.createCreature(creature1);
		verify(creatureManager, times(1)).addCreature(creature1);
	}
	
	@Test
	public void updateCreatureTest() {
		creatureService.createCreature(creature2);
		verify(creatureManager, times(1)).addCreature(creature2);
		creature2.setName("newName");
		creature2.setHeight(0.5);
		creatureService.updateCreature(creature2);
		verify(creatureManager, times(1)).updateCreature(creature2);
	}
		
	@Test
	public void deleteCreatureTest() {
		creatureService.createCreature(creature1);
		creatureService.deleteCreature(creature1.getId());
		verify(creatureManager, times(1)).deleteCreature(creatureManager.findCreature(creature1.getId()));
	}
	
	@Test
	public void getAllCreaturesTest() {
		creatureService.createCreature(creature1);
		creatureService.createCreature(creature2);
		creatureService.createCreature(creature3);
		creatureService.getAllCreatures();
		verify(creatureManager, times(1)).findAllCreatures();
		when(creatureManager.findAllCreatures()).thenReturn(creatures);
		Assert.assertEquals(creatureService.getAllCreatures(), creatures);
	}
	
	@Test
	public void getCreaturesInCircleTest() {
		double radius = 2.0;
		creature1.addArea(area);
		creature2.addArea(area);
		creature3.addArea(area2);
		
		
		creatureService.createCreature(creature1);
		creatureService.createCreature(creature2);
		creatureService.createCreature(creature3);
		
		List<Creature> testCreatures = new ArrayList<>();
		testCreatures.add(creature1);
		testCreatures.add(creature2);
		
		verify(creatureManager, times(1)).addCreature(creature1);
		verify(creatureManager, times(1)).addCreature(creature2);
		verify(creatureManager, times(1)).addCreature(creature3);
		
		when(areaManager.findAllAreas()).thenReturn(areas);
		
		Assert.assertEquals(creatureService.getCreaturesInCircle(radius, 4.0, 4.1), testCreatures);
		
		
	}
	
	@Test
	public void getCreaturesByAreaTest() {
		creatureService.createCreature(creature1);
		creatureService.createCreature(creature2);
		
		creature1.addArea(area);
		creature2.addArea(area);
		
		List<Creature> testCreatures = new ArrayList<>();
		testCreatures.add(creature1);
		testCreatures.add(creature2);
		
		creatureService.getCreaturesByArea(area.getName());
		verify(areaManager, times(1)).findAreaByName(area.getName()).getCreatures();
		
		when(areaManager.findAreaByName(area.getName())).thenReturn(area);
		Assert.assertEquals(creatureService.getCreaturesByArea(area.getName()), testCreatures);
		
	}
	
	@Test
	public void getCreatureTest() {
		creatureService.createCreature(creature3);
		creatureService.getCreature(creature3.getId());
		verify(creatureManager, times(1)).findCreature(creature3.getId());
	}
}
