package cz.muni.fi.pa165.service.facade.test;

import cz.muni.fi.pa165.dto.AreaDTO;
import cz.muni.fi.pa165.dto.CreatureDTO;
import cz.muni.fi.pa165.dto.WeaponDTO;
import cz.muni.fi.pa165.persistence.entity.Area;
import cz.muni.fi.pa165.persistence.entity.Creature;
import cz.muni.fi.pa165.persistence.entity.Weapon;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.CreatureService;
import cz.muni.fi.pa165.service.configuration.ServiceConfiguration;
import cz.muni.fi.pa165.service.facade.CreatureFacadeImpl;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.service.spi.ServiceException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.mockito.Mockito.*;

/**
 *
 * @author Ondrej Klein
 */
@ContextConfiguration(classes=ServiceConfiguration.class)
public class CreatureFacadeTest extends AbstractTransactionalTestNGSpringContextTests {
	@Mock
	private CreatureService creatureService;
	
	private CreatureFacadeImpl creatureFacade;
	
	@Autowired
    private BeanMappingService beanMappingService;
	
	@org.testng.annotations.BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
        creatureFacade = new CreatureFacadeImpl();
        creatureFacade.setCreatureService(creatureService);
        creatureFacade.setBeanMappingService(beanMappingService);
        
    }
	
	private WeaponDTO weapon;
    private CreatureDTO creature1;
	private CreatureDTO creature2;
	private CreatureDTO creature3;
    private List<AreaDTO> areas;
	private List<CreatureDTO> creatures;
    private AreaDTO area;
	private AreaDTO area2;
    
    @BeforeMethod
    public void prepareCreatures(){
        weapon = new WeaponDTO();
        weapon.setName("Chainsaw");
        weapon.setAmmunition("chainz");
        weapon.setGunReach(1.0);
        
        creature1 = new CreatureDTO();
        creature1.setName("Fire zombie");
        creature1.setFerocity(10);
        creature1.setHeight(2.2);
        creature1.setWeakness("Doesn't work when wet");
		
		creature2 = new CreatureDTO();
        creature2.setName("Knife thrower");
        creature2.setFerocity(3);
        creature2.setHeight(1.78);
        creature2.setWeakness("Brings knives into a gun fight");
		
		creature3 = new CreatureDTO();
        creature3.setName("Strangler");
        creature3.setFerocity(6);
        creature3.setHeight(3.14);
        creature3.setWeakness("Slow");
		
		creatures = new ArrayList<CreatureDTO>();
		creatures.add(creature1);
		creatures.add(creature2);
		creatures.add(creature3);
		
		area = new AreaDTO();
        area.setName("Area1");
		area.setLatitude(5.0);
		area.setLongitude(5.0);
		
		area2 = new AreaDTO();
		area2.setLatitude(10.0);
		area2.setLongitude(10.0);
		
		areas = new ArrayList<>();
		areas.add(area);
		areas.add(area2);
    }
	
	@Test
	public void createCreatureTest() {
		Creature res = beanMappingService.mapTo(creature2, Creature.class);
		res.setId(123l);
		when(creatureService.createCreature(beanMappingService.mapTo(creature2, Creature.class))).thenReturn(res);
		creatureFacade.createCreature(creature2);
		verify(creatureService, times(1)).createCreature(res);		
	}
	
	@Test
	public void updateCreatureTest() {
		creatureFacade.updateCreature(creature1);
		verify(creatureService, times(1)).updateCreature(beanMappingService.mapTo(creature1, Creature.class));
		
	}
	
	@Test
	public void deleteCreatureTest() {
		creatureFacade.deleteCreature(123l);
		verify(creatureService, times(1)).deleteCreature(123l);
	}
	
	@Test
	public void getAllCreaturesTest() {
		creatureFacade.getAllCreatures();
		verify(creatureService, times(1)).getAllCreatures();
	}
	
	@Test
	public void getCreaturesInCircleTest() {
		creatureFacade.getCreaturesInCircle(1.0, 2.0, 3.0);
		verify(creatureService, times(1)).getCreaturesInCircle(1.0, 2.0, 3.0);
	}
	
	@Test
	public void getCreaturesByAreaTest() {
		creatureFacade.getCreaturesByArea("Moravia");
		verify(creatureService, times(1)).getCreaturesByArea("Moravia");
	}
	
	@Test
	public void getCreaturesByWeaponTest() {
		creatureFacade.getCreaturesByWeapon("chainsaw");
		verify(creatureService, times(1)).getCreaturesByWeapon("chainsaw");
	}
	
	@Test
	public void getCreatureTest() {  
		when(creatureService.getCreature(123l)).thenReturn(beanMappingService.mapTo(creature1, Creature.class));
		creatureFacade.getCreature(123l);
		verify(creatureService, times(1)).getCreature(123l);
	}

}
