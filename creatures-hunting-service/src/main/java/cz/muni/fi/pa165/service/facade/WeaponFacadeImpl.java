package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.dto.CreatureDTO;
import cz.muni.fi.pa165.dto.WeaponDTO;
import cz.muni.fi.pa165.facade.WeaponFacade;
import cz.muni.fi.pa165.persistence.entity.Creature;
import cz.muni.fi.pa165.persistence.entity.Weapon;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.WeaponService;
import java.util.List;
import javax.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Ondrej Klein
 */
public class WeaponFacadeImpl implements WeaponFacade {
	
	@Inject
	public WeaponService weaponService;
	
	@Autowired
    private BeanMappingService beanMappingService;

	@Override
	public Long addWeapon(WeaponDTO weapon) {
		Weapon newWeapon = weaponService.addWeapon(beanMappingService.mapTo(weapon, Weapon.class));
		return newWeapon.getId();
	}

	@Override
	public void updateWeapon(WeaponDTO weapon) {
		weaponService.updateWeapon(beanMappingService.mapTo(weapon, Weapon.class));
	}

	@Override
	public void assignCreature(WeaponDTO weapon, CreatureDTO creature) {
		weaponService.assignCreature(beanMappingService.mapTo(weapon, Weapon.class), beanMappingService.mapTo(creature, Creature.class));
	}

	@Override
	public void removeCreature(WeaponDTO weapon, CreatureDTO creature) {
		weaponService.removeCreature(beanMappingService.mapTo(weapon, Weapon.class), beanMappingService.mapTo(creature, Creature.class));
		
	}

	@Override
	public void removeWeapon(WeaponDTO weapon) {
		weaponService.deleteWeapon(beanMappingService.mapTo(weapon, Weapon.class));
	}

	@Override
	public WeaponDTO getWeaponById(Long id) {
		return beanMappingService.mapTo(weaponService.findWeaponById(id), WeaponDTO.class);
	}

	@Override
	public WeaponDTO getWeaponByName(String name) {
		return beanMappingService.mapTo(weaponService.findWeaponByName(name), WeaponDTO.class);
	}

	@Override
	public List<WeaponDTO> getWeaponsByCreature(CreatureDTO creature) {
		return beanMappingService.mapTo(weaponService.findWeaponsByCreature(beanMappingService.mapTo(creature, Creature.class)), WeaponDTO.class);
	}

	@Override
	public List<WeaponDTO> getAllWeapons() {
		return beanMappingService.mapTo(weaponService.findAllWeapons(), WeaponDTO.class);
	}
	
}
